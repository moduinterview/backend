//package com.moduinterview.user.auth.filter;
//
//import com.moduinterview.user.component.JwtTokenProvider;
//import java.io.IOException;
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//
//@Slf4j
//@Component
//@RequiredArgsConstructor
//public class JwtAuthFilter extends OncePerRequestFilter {
//
//  private final JwtTokenProvider jwtTokenProvider;
//
//
//  @Override
//  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
//      FilterChain filterChain) throws ServletException, IOException {
//
//    String token = jwtTokenProvider.resolveTokenFromRequest(request);
//
//    if (jwtTokenProvider.validateToken(token)) {
//      Authentication authentication = jwtTokenProvider.getAuthentication(token);
//      SecurityContextHolder.getContext().setAuthentication(authentication);
//    }
//
//    filterChain.doFilter(request, response);
//  }
//}
