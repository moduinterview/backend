//package com.moduinterview.user.auth.filter;
//
//import io.netty.handler.codec.http.HttpResponse;
//import io.netty.handler.codec.http.HttpResponseStatus;
//import java.io.IOException;
//import javax.servlet.Filter;
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.http.HttpServletRequest;
//
//public class JwtFilter implements Filter {
//
//
//  @Override
//  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
//      throws IOException, ServletException {
//
//    HttpServletRequest httpServletRequest = (HttpServletRequest) request;
//    HttpResponse httpServletResponse = (HttpResponse) response;
//
//
//    String headerAuth = httpServletRequest.getHeader("Authorization");
//
//    //JWT 인증
//    if(headerAuth.equals("") || headerAuth == null){
//      httpServletResponse.setStatus(HttpResponseStatus.valueOf(401));
//      return;
//    }
//
//
//    chain.doFilter(request, response);
//
//  }
//}
