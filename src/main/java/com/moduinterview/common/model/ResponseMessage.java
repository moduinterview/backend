package com.moduinterview.common.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseMessage {

  ResponseMessageHeader header;
  Object body;

  // Fail ResponseMessage with Data
  public static ResponseMessage fail(String message, Object data) {
    return ResponseMessage.builder()
        .header(ResponseMessageHeader.builder()
            .result(false)
            .resultCode("")
            .message(message)
            .status(400)
            .build())
        .body(data)
        .build();
  }

  // Fail ResponseMessage without Data
  public static ResponseMessage fail(String message) {
    return fail(message, null);
  }

  // Fail ResponseMessage without message
  public static ResponseMessage fail(Object data) {
    return fail("Fail", data);
  }

  // Fail ResponseMessage with no parameters
  public static ResponseMessage fail() {
    return fail("Fail", null);
  }


  // Success ResponseMessage with Data and no message
  public static ResponseMessage success(String message, Object data) {
    return ResponseMessage.builder()
        .header(ResponseMessageHeader.builder()
            .result(true)
            .resultCode("")
            .message(message)
            .status(HttpStatus.OK.value())
            .build())
        .body(data)
        .build();
  }

  // Success ResponseMessage without message
  public static ResponseMessage success(Object data) {
    return success("Success", data);
  }


  // Success ResponseMessage without Data
  public static ResponseMessage success(String message) {
    return success(message, null);
  }

  // Success ResponseMessage with no parameters
  public static ResponseMessage success() {
    return success("Success", null);
  }
}
