package com.moduinterview.common.config;

//import com.moduinterview.user.auth.filter.JwtAuthFilter;

import com.moduinterview.user.auth.filter.JwtAuthenticationFilter;
import com.moduinterview.user.auth.filter.JwtAuthorizationFilter;
import com.moduinterview.user.component.JwtTokenProvider;
import com.moduinterview.user.service.LoginServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.filter.CorsFilter;

@Slf4j
@Configuration
@EnableWebSecurity // Spring Security 설정할 클래스라고 정의
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private final CorsFilter corsFilter;
  private final JwtTokenProvider jwtTokenProvider;
  private final LoginServiceImpl loginServiceImpl;
//  private final JwtAuthFilter jwtAuthFilter;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
//        .addFilterBefore(new JwtFilter(), SecurityContextPersistenceFilter.class)
        .addFilter(new JwtAuthenticationFilter(authenticationManager(),jwtTokenProvider,
            loginServiceImpl))
        .addFilterBefore(new JwtAuthorizationFilter(jwtTokenProvider),JwtAuthenticationFilter.class)
        .httpBasic().disable()
        .csrf().disable()
        .formLogin().disable()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()

        .authorizeRequests()
        .antMatchers("/api-user/**")
        .access("hasRole('USER') or hasRole('ADMIN')")
        .antMatchers("/api-admin/**")
        .access("hasRole('ADMIN')")
        .anyRequest().permitAll()
        .and()
        .addFilter(corsFilter); // @CrossOrigin(인증X), 시큐리티 필터에 등록 인증(O)
//        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
  }


  //설정과 관련된 부분
  @Override
  public void configure(final WebSecurity web) {
    web.ignoring()
        .antMatchers("/h2-console/**", "/favicon.ico", "/error");
  }

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }



}

