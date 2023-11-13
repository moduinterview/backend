package com.moduinterview.interview.dto;

import com.moduinterview.interview.entity.Comment;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import lombok.Builder;

@Builder
@Schema(description = "댓글 조회용 DTO")
public class CommentResponseDto {

  @Schema(description = "댓글 ID")
  public Long commentId;
  @Schema(description = "인터뷰 ID")
  public Long interviewId;
  @Schema(description = "댓글 작성자명")
  public String writerName;
  @Schema(description = "댓글 내용")
  public String content;
  @Schema(description = "댓글 타입 //ask, answer")
  public String commentType; //ask or answer
  @Schema(description = "댓글 부모 댓글 순서")
  public int parentOrder;
  @Schema(description = "댓글 자식 댓글 순서")
  public int childOrder;
  @Schema(description = "댓글 작성일")
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
