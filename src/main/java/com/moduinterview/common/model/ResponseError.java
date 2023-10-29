package com.moduinterview.common.model;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ResponseError {

  private String field;
  private String message;

  public static ResponseError of(FieldError error) {
    return ResponseError.builder()
        .field(error.getField())
        .message(error.getDefaultMessage())
        .build();
  }

  public static List<ResponseError> of(List<ObjectError> errors) {
    List<ResponseError> responseErrors = new ArrayList<>();

    if (errors != null) {
      errors.stream().forEach(e -> responseErrors.add(ResponseError.of((FieldError) e)));
    }

    return responseErrors;
  }
}
