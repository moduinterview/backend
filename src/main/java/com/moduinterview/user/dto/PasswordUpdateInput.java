package com.moduinterview.user.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Getter;

@Getter
public class PasswordUpdateInput {

  @Size(min = 4, max = 20, message = "비밀번호는 4~20자 사이로 입력해야합니다.")
  @NotBlank(message = "기존 비밀번호는 필수 항목입니다.")
  private String password;

  @Size(min = 4, max = 20, message = "비밀번호는 4~20자 사이로 입력해야합니다.")
  @NotBlank(message = "새 비밀번호는 필수 항목입니다.")
  private String newPassword;

}
