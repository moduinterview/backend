package com.moduinterview.user.service;

import com.moduinterview.common.dto.SignUpRequestDto;
import com.moduinterview.common.exception.UserNotFoundException;
import com.moduinterview.common.model.ServiceResult;
import com.moduinterview.user.component.JwtTokenProvider;
import com.moduinterview.user.component.MailComponents;
import com.moduinterview.user.dto.FindPasswordRequestDto;
import com.moduinterview.user.dto.PasswordUpdateInput;
import com.moduinterview.user.entity.User;
import com.moduinterview.user.enums.OauthType;
import com.moduinterview.user.enums.UserRole;
import com.moduinterview.user.enums.UserStatus;
import com.moduinterview.user.repository.UserRepository;
import com.moduinterview.user.utility.PasswordUtils;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

@Service
@RequiredArgsConstructor
public class UserServiceImpl {

  private final UserRepository userRepository;
  private final MailComponents mailComponents;
  private final JwtTokenProvider jwtTokenProvider;

  @Transactional(rollbackOn = Exception.class)
  public ServiceResult signUp(SignUpRequestDto requestDto) {

    // 이메일 중복 체크
    if (userRepository.findByEmail(requestDto.getEmail()).isPresent()) {
      return ServiceResult.fail("이미 가입된 이메일입니다.");
    }

    //이메일 인증키 생성
    String uuid = UUID.randomUUID().toString();

    // 비밀번호 암호화
    String encryptedPasswords = PasswordUtils.getEncodedPassword(requestDto.getPassword());
    LocalDateTime now = LocalDateTime.now();

    //user 생성
    try {
      User createdUser = User.builder()
          .phone(requestDto.getPhone())
          .status(UserStatus.INACTIVE)
          .oAuthType(OauthType.NATIVE)
          .role(UserRole.ROLE_USER)
          .email(requestDto.getEmail())
          .emailAuthKey(uuid)
          .emailAuthYn(false)
          .password(encryptedPasswords)
          .gender(requestDto.getGender())
          .regDate(now)
          .name(requestDto.getUserName())
          .build();
      userRepository.save(createdUser);

//      이메일 발송
      String email = requestDto.getEmail();
      String userName = requestDto.getUserName();
      String title = userName + "님의" + " ModuInterview 가입을 축하드립니다.";
      String Contents = "안녕하세요. " + userName + "님, 회원가입을 축하드립니다.\n"
          + "아래 링크를 클릭하시면 이메일 인증이 완료됩니다.\n"
          + "http://localhost:8080/user/email-auth/" + email + "/" + uuid;
      boolean mailSendSuccess = mailComponents.sendEmail(email, title, Contents);
      if (!mailSendSuccess) {
        return ServiceResult.fail("회원가입에 성공하였으나, 이메일 발송에 실패하였습니다."
            + " 메일 인증에 문제가 있을 수 있습니다.");
      }

    } catch (Exception e) {
      TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
      return ServiceResult.fail("회원가입에 실패하였습니다."
          + " 기입항목의 유효성 및 누락된 항목을 확인해주세요.");
    }
    return ServiceResult.success("회원가입에 성공하였습니다.가입한 메일로 인증을 완료해주세요.");
  }

  @Transactional
  public ServiceResult emailAuth(String email, String authKey) {

    User user = userRepository.findByEmail(email)
        .orElseThrow(() -> new UsernameNotFoundException("인증이 필요한 사용자를 찾을 수 없습니다."));
    if (!user.getEmailAuthKey().equals(authKey)) {
      return ServiceResult.fail("인증키가 일치하지 않습니다.");
    }
    user.setEmailAuthYn(true);
    user.setStatus(UserStatus.ACTIVE);
    user.setEmailAuthDate(LocalDateTime.now());
    userRepository.save(user);

    return ServiceResult.success("이메일 인증이 완료되었습니다.");
  }
  public ServiceResult updatePassword(User user, PasswordUpdateInput input){
    String encodedPassword = PasswordUtils.getEncodedPassword(input.getPassword());
    user.setPassword(encodedPassword);
    userRepository.save(user);
    return ServiceResult.success("비밀번호가 변경되었습니다.");
  }

  @Transactional
  public ServiceResult updateUser(HttpServletRequest request,
      @NotNull SignUpRequestDto requestDto) {
    User user = findUserFromRequest(request);
    user.setPhone(requestDto.getPhone());
    user.setGender(requestDto.getGender());
    user.setName(requestDto.getUserName());
    userRepository.save(user);
    return ServiceResult.success("회원정보가 수정되었습니다.");
  }

  @Transactional
  public ServiceResult findPassword(FindPasswordRequestDto requestDto) {
    String email = requestDto.getEmail();
    Optional<User> optionalUser = userRepository.findByEmail(email);

    if (!optionalUser.isPresent()) {
      return ServiceResult.fail("가입되지 않은 이메일입니다.");
    }
    User user = optionalUser.get();

    if (!user.getPhone().equals(requestDto.getPhoneNumber())) {
      return ServiceResult.fail("가입된 이메일과 휴대폰 번호가 일치하지 않습니다.");
    }

    String uuid = UUID.randomUUID().toString();
    String encryptedPassword = PasswordUtils.getEncodedPassword(uuid);
    user.setPassword(encryptedPassword);
    userRepository.save(user);

    String title = "모두 인터뷰 비밀번호 찾기 메일입니다.";
    String Contents = "안녕하세요. " + user.getName() + "님, 모두의 인터뷰 비밀번호 찾기 메일입니다.\n"
        + "아래의 임시 비밀번호를 로그인하신 후 비밀번호를 변경해 주세요. \n"
        + "임시 비밀번호: " + encryptedPassword;

    boolean mailSendSuccess = mailComponents.sendEmail(email, title, Contents);
    if (!mailSendSuccess) {
      return ServiceResult.fail("임시비밀번호를 발급하였으나, 이메일 발송에 실패하였습니다.");
    }
    return ServiceResult.success("임시비밀번호 메일이 발송되었습니다.");
  }


  public ServiceResult deleteUser(HttpServletRequest request) {
    User user = findUserFromRequest(request);
    user.setStatus(UserStatus.DELETED);
    userRepository.save(user);
    return ServiceResult.success("회원탈퇴가 완료되었습니다.");
  }

  public User findUserFromRequest(HttpServletRequest request) {
    String token = jwtTokenProvider.resolveTokenFromRequest(request);
    String userId = jwtTokenProvider.getUserId(token);
    return userRepository.findById(Long.valueOf(userId))
        .orElseThrow(() -> new UserNotFoundException("회원정보가 존재하지 않습니다."));
  }


}
