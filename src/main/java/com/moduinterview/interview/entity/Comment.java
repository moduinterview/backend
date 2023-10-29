package com.moduinterview.interview.entity;

import com.moduinterview.interview.dto.CommentInput;
import com.moduinterview.user.entity.User;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class Comment {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "interview_id")
  private Interview interview;


  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User writer;

  private String content;
  private boolean isAnswer;

  //QNA 질문자수
  @NotNull
  private int parentOrder;

  //QNA 답변자수
  @NotNull
  private int childOrder;

  private LocalDateTime createdAt;
  boolean isDeleted;


  public String getAskerName() {
    return writer.getName();
  }
  public String getInterviewWriterName() {
    return interview.getUser().getName();
  }

  public static Comment createCommmentFrom(CommentInput input, User user,Interview interview) {

    return Comment.builder()
        .interview(interview)
        .content(input.getContent())
        .writer(user)
//        .parentOrder(input.getParentOrder())
//        .childOrder(input.getChildOrder())
        .content(input.getContent())
        .isDeleted(false)
//        .isAnswer()
        .createdAt(LocalDateTime.now())
        .build();
  }
}
