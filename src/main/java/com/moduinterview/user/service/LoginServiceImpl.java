package com.moduinterview.user.service;

import com.moduinterview.common.exception.UserNotFoundException;
import com.moduinterview.user.dto.LoginRequestDto;
import com.moduinterview.user.entity.User;
import com.moduinterview.user.enums.UserStatus;
import com.moduinterview.user.exception.LoginException;
import com.moduinterview.user.repository.UserRepository;
import com.moduinterview.user.utility.PasswordUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class LoginServiceImpl implements UserDetailsService {

  private final UserRepository userRepository;
  private final AuthenticationManagerBuilder authenticationManagerBuilder;

  //로그인 시도시 호출되는 서비스

  @Override
  public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {

    User user = userRepository.findById(Long.valueOf(userId))
        .orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다. ->" + userId));

    if (user.getStatus().equals(UserStatus.INACTIVE)) {
      throw new LoginException("이메일 활성화 이후에 로그인 하세요.");
    } else if (user.getStatus().equals(UserStatus.DELETED)) {
      throw new LoginException("탈퇴한 회원입니다.");
    } else if (user.getStatus().equals(UserStatus.BLOCKED)) {
      throw new LoginException("차단된 회원입니다.");
    }
//    return createUserDetails(user);
    return user;
  }

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

  //엑세스토큰 반환
  public String getUserIdFromAuthentication(LoginRequestDto loginRequestDto) {
    User user = authenticate(loginRequestDto);
    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
        user.getId(), loginRequestDto.getPassword(), user.getAuthorities());

    Authentication authentication = authenticationManagerBuilder.getObject()
        .authenticate(authenticationToken);
    //PrincipalDetails에서 getName을 userId를 반환하도록 설정 했다.
    return authentication.getName();
  }

  //signIn

  public User authenticate(LoginRequestDto requestDto) {
    User user = userRepository.findByEmail(requestDto.getEmail())
        .orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다."));

    if (!PasswordUtils.isEqualPassword(requestDto.getPassword(), user.getPassword())) {
      throw new LoginException("비밀번호가 일치하지 않습니다.");
    }
    return user;
  }


}
