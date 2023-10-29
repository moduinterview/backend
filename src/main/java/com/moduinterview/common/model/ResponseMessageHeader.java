package com.moduinterview.common.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseMessageHeader {

  private boolean result;
  private String resultCode;
  private String message;
  private int status;

}
