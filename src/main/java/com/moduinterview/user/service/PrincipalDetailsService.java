//package com.moduinterview.user.service;
//
//import com.moduinterview.common.exception.UserNotFoundException;
//import com.moduinterview.user.entity.User;
//import com.moduinterview.user.repository.UserRepository;
//import java.util.Collections;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class PrincipalDetailsService implements UserDetailsService {
//
//  private final UserRepository userRepository;
//
//  @Override
//  public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
//    return userRepository.findByEmail(userEmail).map(this::createUserDetails)
//        .orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다."));
//  }
//
//  private UserDetails createUserDetails(User user) {
//    String role = user.getRole().getStringRoles();
//    GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role);
//
//    return new org.springframework.security.core.userdetails.User(
//        String.valueOf(user.getId()),
//        user.getPassword(),
//        Collections.singletonList(grantedAuthority)
//    );
//  }
//
//}
