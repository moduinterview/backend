//package com.moduinterview.user.auth;
//
//import com.moduinterview.user.entity.User;
//import com.moduinterview.user.enums.UserStatus;
//import java.util.ArrayList;
//import java.util.Collection;
//import lombok.Data;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//@Data
//public class PrincipalDetails implements UserDetails {
//
//  private User user;
//
//  public PrincipalDetails(User user) {
//    this.user = user;
//  }
//
//  @Override
//  public Collection<? extends GrantedAuthority> getAuthorities() {
//    Collection<GrantedAuthority> authorities = new ArrayList<>();
//    user.getRoleList().forEach(r -> {
//      authorities.add(() -> r);
//    });
//
//    return authorities;
//  }
//
//  @Override
//  public String getPassword() {
//    return user.getPassword();
//  }
//
//  @Override
//  public String getUsername() {
//    return String.valueOf(user.getId());
//  }
//
//  @Override
//  public boolean isAccountNonExpired() {
//    return true; // 계정 만료 여부
//  }
//
//  @Override
//  public boolean isAccountNonLocked() {
//
//    return  !(user.getStatus() == UserStatus.BLOCKED); // 계정 잠금 여부
//  }
//
//  @Override
//  public boolean isCredentialsNonExpired() {
//    return true; // 계정 패스워드 만료 여부
//  }
//
//  @Override
//  public boolean isEnabled() {
//    return isAccountNonExpired() && isAccountNonLocked() && isCredentialsNonExpired();
//  }
//}
