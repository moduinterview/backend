package com.moduinterview.user.auth.filter;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.moduinterview.user.component.JwtTokenProvider;
import com.moduinterview.user.dto.LoginRequestDto;
import com.moduinterview.user.dto.TokenResponseDto;
import com.moduinterview.user.entity.User;
import com.moduinterview.user.service.LoginServiceImpl;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

  private final AuthenticationManager authenticationManager;
  private final JwtTokenProvider jwtTokenProvider;
  private final LoginServiceImpl loginServiceImpl;

  //login 요청을 하면 로그인 시도를 위해서 실행되는 함수
  @Override
  public Authentication attemptAuthentication(HttpServletRequest request,
      HttpServletResponse response) throws AuthenticationException {

    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true)
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    try {
      LoginRequestDto requestDto = objectMapper.readValue(request.getInputStream(),
          LoginRequestDto.class);
      User user = loginServiceImpl.authenticate(requestDto);

      UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
          user.getId(), requestDto.getPassword());

      return authenticationManager.authenticate(authenticationToken);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

  }

  @Override
  protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
      FilterChain chain, Authentication authResult) throws IOException {
    System.out.println("successfulAuthentication 실행됨 : 인증이 완료되었다는 뜻임");
    UserDetails userDetails = (UserDetails) authResult.getPrincipal();

    TokenResponseDto accessTokenDto = jwtTokenProvider.createAccessToken(userDetails.getUsername());
    String token = accessTokenDto.getAccessToken();

    response.addHeader("Authorization", "Bearer " + token);
    response.getWriter().write(accessTokenDto.toString());
  }
}