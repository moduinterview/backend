package com.moduinterview.user.dto;


import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class LoginRequestDto {

  @NotBlank(message = "이메일 항목은 필수 입력입니다.")
  private String email;

  @NotBlank(message = "비밀번호 항목은 필수입니다.")
  private String password;

}
