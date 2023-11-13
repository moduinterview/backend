package com.moduinterview.user.service;

import com.moduinterview.user.entity.User;
import com.moduinterview.user.enums.Gender;
import com.moduinterview.user.enums.OauthType;
import java.time.LocalDateTime;
import lombok.Builder;


@Builder
public class UserInfoResponseDto {

  private String email; //oauth
  private String name;
  private OauthType oAuthType; //google, naver, kakao, native
  private String oauthUserName;
  private String oauthId;
  private Gender gender;
  private String phone;

  private LocalDateTime regDate;

//  private String roles; //User, Admin

  public static UserInfoResponseDto createUserInfoResponseOf(User user) {
    return UserInfoResponseDto.builder()
        .email(user.getEmail())
        .name(user.getName())
        .oAuthType(user.getOAuthType())
        .oauthUserName(user.getOauthUserName())
        .oauthId(user.getOauthId())
        .gender(user.getGender())
        .phone(user.getPhone())
        .regDate(user.getRegDate())
        .build();

  }

}
