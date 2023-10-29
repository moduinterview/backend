package com.moduinterview.interview.dto;

import com.moduinterview.interview.entity.Comment;
import java.time.LocalDate;
import lombok.Builder;

@Builder
public class CommentResponseDto {

  public Long commentId;
  public Long interviewId;
  public String writerName;
  public String content;
  public String commentType; //ask or answer
  public int parentOrder;
  public int childOrder;
  public LocalDate createdAt;


  public static CommentResponseDto of (Comment comment) {
    return CommentResponseDto.builder()
        .commentId(comment.getId())
        .interviewId(comment.getInterview().getId())
        .writerName(comment.getWriter().getName())
        .content(comment.getContent())
        .commentType(comment.isAnswer() ? "answer" : "ask")
        .parentOrder(comment.getParentOrder())
        .childOrder(comment.getChildOrder())
        .createdAt(comment.getCreatedAt().toLocalDate())
        .build();

  }

}
