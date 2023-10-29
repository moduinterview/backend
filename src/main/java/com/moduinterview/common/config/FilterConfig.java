//package com.moduinterview.config;
//
//import com.moduinterview.filter.JwtFilter;
//import org.springframework.boot.web.servlet.FilterRegistrationBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class FilterConfig {
//
//  @Bean
//  public FilterRegistrationBean<JwtFilter> jwtFilter() {
//    FilterRegistrationBean<JwtFilter> bean = new FilterRegistrationBean<>(new JwtFilter());
//    bean.addUrlPatterns("/user/*");
//    bean.addUrlPatterns("/admin/*");
//    bean.setOrder(0);
//    return bean;
//  }
//
//
//
//}
