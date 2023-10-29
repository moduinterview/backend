package com.moduinterview.common.model;

import org.springframework.http.ResponseEntity;

public class ResponseResult {


  public static ResponseEntity<?> fail(String message) {
    return fail(message, null);
  }

  public static ResponseEntity<?> fail(String message, Object data) {
    return ResponseEntity.badRequest().body(ResponseMessage.fail(message, data));
  }


  public static ResponseEntity<?> success() {
    return ResponseResult.success("Service success");
  }

  public static ResponseEntity<?> success(Object data) {
    return ResponseResult.success("Service success", data);
  }

  public static ResponseEntity<?> success(String message, Object data) {
    return ResponseEntity.ok().body(ResponseMessage.success(message, data));
  }

  public static ResponseEntity<?> result(ServiceResult serviceResult) {
    boolean isFail = serviceResult.isFail();
    boolean hasData = serviceResult.getData() != null;
    boolean hasMessage = serviceResult.getMessage() != null;

    if (isFail && !hasData) {
      return fail(serviceResult.getMessage());
    } else if (isFail && hasData) {
      return fail(serviceResult.getMessage(), serviceResult.getData());
    } else if (hasData) {
      return success(serviceResult.getMessage(), serviceResult.getData());
    } else if (hasMessage) {
      return success(serviceResult.getMessage());
    } else {
      return success();
    }

  }

}
