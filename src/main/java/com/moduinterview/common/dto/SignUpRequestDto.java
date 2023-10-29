package com.moduinterview.common.dto;

import com.moduinterview.user.enums.Gender;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Getter
public class SignUpRequestDto {

  @Email(message = "이메일 형식에 맞게 작성해주세요.", regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")
  @NotBlank(message = "이메일을 입력해주세요.")
  String email;


  @Length(min = 8, max = 20, message = "비밀번호는 8자리 이상 20자리 이하로 작성해주세요.")
  String password;

  String userName;

  @Enumerated(javax.persistence.EnumType.STRING)
  Gender gender;

  @Pattern(regexp = "[0-9]{10,11}", message = "010-xxxx-xxxx 형식으로 입력해주세요.")
  String phone;
}
