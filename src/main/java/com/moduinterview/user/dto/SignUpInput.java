package com.moduinterview.user.dto;

import com.moduinterview.user.enums.Gender;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Getter
public class SignUpInput {

  @Email(message = "이메일 형식에 맞게 작성해주세요.", regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")
  private String email;

  //TODO: 비밀번호와 비밀번호 확인에 대한 validation은 프론트?
  @NotBlank(message = "비밀번호는 필수 항목입니다.")
  @Size(min = 4, max = 20, message = "비밀번호는 4~20자 사이로 입력해야합니다.")
  private String password;


  @Length(min = 1, max = 1, message = "성별은 1자리로 작성해주세요.")
  //Char(1) , M/F
  private Gender gender;

  @NotBlank(message = "이름은 필수 항목입니다.")
  private String userName;


  @Length(min = 10, max = 11, message = "전화번호는 10~11자리로 작성해주세요.(01012345678)")
  private String phone;
}
