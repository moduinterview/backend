package com.moduinterview.interview.entity;

import com.moduinterview.user.entity.User;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class InterviewHits {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @ManyToOne(fetch = javax.persistence.FetchType.LAZY)
  @JoinColumn(name = "interview_id")
  private Interview interview;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  private LocalDateTime regDate;

}

