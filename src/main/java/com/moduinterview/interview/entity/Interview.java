package com.moduinterview.interview.entity;

import com.moduinterview.interview.dto.InterviewInput;
import com.moduinterview.interview.enums.InterviewMethod;
import com.moduinterview.interview.enums.InterviewStatus;
import com.moduinterview.interview.enums.InterviewType;
import com.moduinterview.interview.enums.Region;
import com.moduinterview.interview.enums.TimeUnit;
import com.moduinterview.user.entity.User;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
@Entity
public class Interview {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  //TODO : 리크루트인원 0명일때는 리크루트 불가능하게 설정

  private Integer recruitNum;

  @Length(min = 1, max = 30, message = "제목은 1~30자 길이로 작성해주세요.")
  private String title;

  private LocalDate startDate;
  private LocalDate dueDate;
  private LocalDate interviewDate;

  private Integer duration;
  @Enumerated(value = javax.persistence.EnumType.STRING)
  private TimeUnit durationTimeUnit;

  private String description;

  @Enumerated(value = javax.persistence.EnumType.STRING)
  private InterviewMethod interviewMethod;
  @Enumerated(value = javax.persistence.EnumType.STRING)
  private InterviewType interviewType;

  private long rewardAmount;

  @Enumerated(value = javax.persistence.EnumType.STRING)
  private InterviewStatus interviewStatus;
  private String contactName;
  private String contactPhone;
  @Email(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$", message = "이메일 형식이 아닙니다.")
  private String contactEmail;
  private LocalDateTime regDate;
  private LocalDateTime modifiedDate;
  private String thumbnailUrl;
  @Enumerated(value = javax.persistence.EnumType.STRING)
  private Region region;

  private Boolean privateInfoAgreement;

  public static Interview createInterviewFrom(InterviewInput interviewInput, User user) {

    return Interview.builder()
        .user(user)
        .recruitNum(interviewInput.getRecruitNum())
        .title(interviewInput.getTitle())

        .startDate(interviewInput.getStartDate())
        .dueDate(interviewInput.getDueDate())
        .duration(interviewInput.getDuration())
        .durationTimeUnit(interviewInput.getDurationTimeUnit())
        .interviewDate(interviewInput.getInterviewDate())
        .description(interviewInput.getDescription())

        .interviewMethod(interviewInput.getInterviewMethod())
        .interviewType(interviewInput.getInterviewType())
        .rewardAmount(interviewInput.getRewardAmount())
        .interviewStatus(InterviewStatus.RECRUITING)
        .contactName(interviewInput.getContactName())
        .contactPhone(interviewInput.getContactPhone())
        .contactEmail(interviewInput.getContactEmail())
        .thumbnailUrl(interviewInput.getThumbnailUrl())
        .regDate(LocalDateTime.now())
        .region(interviewInput.getRegion())
        .privateInfoAgreement(interviewInput.getPrivateInfoAgreement())

        .build();
  }

  //Method for Resetting all the fields of Interview from InterviewInput
  public void setInterviewFrom(InterviewInput interviewInput) {
    this.recruitNum = interviewInput.getRecruitNum();
    this.title = interviewInput.getTitle();
    this.startDate = interviewInput.getStartDate();
    this.dueDate = interviewInput.getDueDate();
    this.duration = interviewInput.getDuration();
    this.durationTimeUnit = interviewInput.getDurationTimeUnit();
    this.interviewDate = interviewInput.getInterviewDate();
    this.description = interviewInput.getDescription();

    this.interviewMethod = interviewInput.getInterviewMethod();
    this.interviewType = interviewInput.getInterviewType();

    this.rewardAmount = interviewInput.getRewardAmount();
    this.contactName = interviewInput.getContactName();
    this.contactPhone = interviewInput.getContactPhone();
    this.contactEmail = interviewInput.getContactEmail();
    this.modifiedDate = LocalDateTime.now();
    this.thumbnailUrl = interviewInput.getThumbnailUrl();
    this.region = interviewInput.getRegion();
  }

}


