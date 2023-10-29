package com.moduinterview.user.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Getter;

@Getter
public class PasswordUpdateInput {

  //TODO: 비밀번호와 비밀번호 확인에 대한 validation은 프론트?
  @NotBlank(message = "비밀번호는 필수 항목입니다.")
  @Size(min = 4, max = 20, message = "비밀번호는 4~20자 사이로 입력해야합니다.")
  private String password;


}
