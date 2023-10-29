package com.moduinterview.user.component;

import com.moduinterview.user.dto.TokenResponseDto;
import com.moduinterview.user.entity.RefreshToken;
import com.moduinterview.user.service.LoginServiceImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

@RequiredArgsConstructor
@Component
@Slf4j
public class JwtTokenProvider {

  //TODO: 토큰 환경변수 설정
  @Value("${jwt.secret-key}")
  private String secretKey;

  public static final String TOKEN_HEADER = "Authorization";
  public static final String TOKEN_PREFIX = "Bearer ";
  public static final String REFRESH_TOKEN_PREFIX = "RefreshToken";
  public static final String KEY_ROLE = "role";

  //TODO: 토큰 유효시간 설정
  private final static long accessTokenValidTime = 60 * 60 * 1000L;    //(1hours : test)
  private final static long refreshTokenValidTime = 24 * 60 * 60 * 1000L;      //(1 day : test)

  private final LoginServiceImpl loginServiceImpl;

  //토큰에서 유저 추출하기
  public TokenResponseDto createAccessToken(String userId) {

    //subject는 유저 아이디
    Claims claims = Jwts.claims().setSubject(userId);
    Date now = new Date();
    LocalDateTime expiredTime = now.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()
        .plusSeconds(accessTokenValidTime / 1000);
    //    LocalDateTime expiredTime = LocalDateTime.now().plusSeconds(accessTokenValidTime / 1000);

    //claims(토큰에 담을 정보), 토큰 발행 시간, 토큰 유효 시간, 암호화 알고리즘
    String accessToken = Jwts.builder()
        .setClaims(claims)
        .setIssuedAt(now)
        .setExpiration(new Date(System.currentTimeMillis() + accessTokenValidTime))
        .signWith(SignatureAlgorithm.HS512, secretKey)
        .compact();

    //리프레시토큰
    String refreshToken = Jwts.builder()
        .setClaims(claims)
        .setIssuedAt(now)
        .setExpiration(new Date(System.currentTimeMillis() + refreshTokenValidTime))
        .signWith(SignatureAlgorithm.HS512, secretKey)
        .compact();

    return TokenResponseDto.builder()
        .accessToken(accessToken)
        .grantType(TOKEN_PREFIX)
        .refreshToken(refreshToken)
        .expiredTime(expiredTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
        .build();
  }

  public Authentication getAuthentication(String token) {
    UserDetails userDetails = loginServiceImpl.loadUserByUsername(getUserId(token));
    //이부분 확인필요. (Credential)
    return new UsernamePasswordAuthenticationToken(userDetails, null,
        userDetails.getAuthorities());
  }

  //토큰 파싱
  private Claims parseClaims(String token) {
    try {
      return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    } catch (ExpiredJwtException e) {
      throw new JwtException(e.getMessage());
    }
  }

  //토큰에서 유저 ID 추출
  public String getUserId(String token) {
    try {
      String userId = parseClaims(token).getSubject(); //토큰에서 유저 아이디 추출
      log.info("getUserId 아이디: " + userId);
      return userId;
    } catch (Exception e) {
      throw new JwtException(e.getMessage());
    }
  }

  //Token 생성

  //토큰 유효성 검사(형식)
  public String resolveTokenFromRequest(HttpServletRequest request) {
    String token = request.getHeader(TOKEN_HEADER);
    if (!ObjectUtils.isEmpty(token) && token.toLowerCase()
        .startsWith(TOKEN_PREFIX.toLowerCase())) {
      return token.substring(TOKEN_PREFIX.length()).trim();
    }
    return null;
  }

  //토큰 유효성 검사(만료)
  public boolean validateToken(String token) {

    if (!StringUtils.hasText(token)) {
      return false;
    }

    try {
//      Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
      Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
      return !claims.getBody().getExpiration().before(new Date());
    } catch (ExpiredJwtException e) {
      log.warn("만료된 JWT 토큰입니다.");
      return false;
    } catch (UnsupportedJwtException e) {
      log.warn("지원되지 않는 JWT 토큰입니다.");
      return false;
    } catch (IllegalArgumentException e) {
      log.warn("JWT 토큰이 잘못되었습니다.");
      return false;
    }catch (SignatureException e) {
      log.warn("JWT Signature이 잘못되었습니다.");
      return false;
    }
  }

  //토큰 재발급
  public String recreationAccessToken(String userId) {

    Claims claims = Jwts.claims().setSubject(userId);
    Date now = new Date();

    return Jwts.builder()
        .setClaims(claims)
        .setIssuedAt(now)
        .setExpiration(new Date(System.currentTimeMillis() + refreshTokenValidTime))
        .signWith(SignatureAlgorithm.HS512, secretKey)
        .compact();
  }

  public String resolveRefreshToken(HttpServletRequest request) {
    String token = request.getHeader(REFRESH_TOKEN_PREFIX);
    log.info("리프레시 토큰: " + request.getHeader(REFRESH_TOKEN_PREFIX));
    if (!ObjectUtils.isEmpty(token) && token.toLowerCase()
        .startsWith(TOKEN_PREFIX.toLowerCase())) {
      return token.substring(TOKEN_PREFIX.length()).trim();
    }
    return null;
  }

  public String validateRefreshToken(RefreshToken token) {
    String refreshToken = token.getRefreshToken();

    try {
      Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey)
          .parseClaimsJws(refreshToken);
      if (!claims.getBody().getExpiration().before(new Date())) {
        return recreationAccessToken(claims.getBody().getSubject());
      }
    } catch (ExpiredJwtException e) {
      log.warn("만료된 JWT 토큰입니다.");
    } catch (UnsupportedJwtException e) {
      log.warn("지원되지 않는 JWT 토큰입니다.");
    } catch (IllegalArgumentException e) {
      log.warn("JWT 토큰이 잘못되었습니다.");
    }
    return null;
  }


}