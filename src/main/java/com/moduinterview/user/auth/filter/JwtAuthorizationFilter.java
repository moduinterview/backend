package com.moduinterview.user.auth.filter;


import com.moduinterview.user.component.JwtTokenProvider;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;


@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

  private final JwtTokenProvider jwtTokenProvider;

  //권한이나 인증이 필요한 주소를 요청시 해당 필터를 거침
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain chain) throws IOException, ServletException {
    String token = jwtTokenProvider.resolveTokenFromRequest(request);

    //토큰이 없거나 유효하지 않으면 인증을 하지 않고 헤더에 exception 메시지 반환.
    if (token == null || !jwtTokenProvider.validateToken(token)) {
      chain.doFilter(request, response);
      return;
    }
    Authentication authentication = jwtTokenProvider.getAuthentication(token);
    SecurityContextHolder.getContext().setAuthentication(authentication);


    // 헤더에 userId값 확인
    request.setAttribute("userId", jwtTokenProvider.getUserId(token));
    System.out.println(request.getAttribute("userId"));

    chain.doFilter(request, response);
  }
}


