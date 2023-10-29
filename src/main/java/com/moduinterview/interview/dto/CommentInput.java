package com.moduinterview.interview.dto;

import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CommentInput {

  public Integer parentOrder;
  public Integer childOrder;
  @NotNull(message = "내용을 입력해주세요.")
  public String content;


}
