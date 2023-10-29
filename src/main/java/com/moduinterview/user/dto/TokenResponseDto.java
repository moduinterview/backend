package com.moduinterview.user.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TokenResponseDto {
  private String grantType;
  private String accessToken;
  private String refreshToken;
  private String expiredTime;
}
