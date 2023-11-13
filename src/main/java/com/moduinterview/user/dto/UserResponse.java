package com.moduinterview.user.dto;

import com.moduinterview.user.entity.User;
import com.moduinterview.user.enums.Gender;
import com.moduinterview.user.enums.OauthType;
import com.moduinterview.user.enums.UserRole;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {


  private long id;
  private String email;

  private Gender gender;
  private String userName;
  private String nickName;
  private LocalDate birthDate;
  private String phone;
  private OauthType oauthType;

  private UserRole role;

  private String zipCode;
  private String profileImageUrl;

  private LocalDateTime regDate;
  private LocalDateTime modifiedDate;

  public static UserResponse getInformationOf(User user) {
    return UserResponse.builder()
        .id(user.getId())
        .email(user.getEmail())
        .gender(user.getGender())
        .userName(user.getName())
        .profileImageUrl(user.getProfileImageUrl())
        .oauthType(user.getOAuthType())
        .phone(user.getPhone())
        .regDate(user.getRegDate())
        .build();
  }

}
