package com.moduinterview.user.dto;

import com.moduinterview.user.enums.Gender;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class UpdateUserRequestDto {


  @NotBlank(message = "이름은 필수 항목입니다.")
  String userName;

  @Enumerated(javax.persistence.EnumType.STRING)
  Gender gender;

  @Pattern(regexp = "[0-9]{10,11}", message = "010-xxxx-xxxx 형식으로 입력해주세요.")
  String phone;
}
