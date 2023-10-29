package com.moduinterview.interview.dto;

import com.moduinterview.interview.entity.Interview;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public class InterviewResult {


  public Long interviewId;
  public String userEmail;
  public Integer recruitNum;
  public String title;
  public LocalDate startDate;
  public LocalDate dueDate;
  public LocalDate interviewDate;
  public Integer duration;
  public String durationTimeUnit;
  public String description;
  public String  interviewMethod;
  public String  interviewType;
  public Long rewardAmount;
  public String contactName;
  public String contactPhone;
  public String contactEmail;
  public LocalDateTime regDate;
  public LocalDateTime modifiedDate;
  public String thumbnailUrl;
  public String region;

  public static InterviewResult from(Interview interview){
    return InterviewResult.builder()
        .interviewId(interview.getId())
        .userEmail(interview.getUser().getEmail())
        .recruitNum(interview.getRecruitNum())
        .title(interview.getTitle())
        .startDate(interview.getStartDate())
        .dueDate(interview.getDueDate())
        .interviewDate(interview.getInterviewDate())
        .duration(interview.getDuration())
        .durationTimeUnit(interview.getDurationTimeUnit().getKoreanTimeUnit())
        .description(interview.getDescription())
        .interviewMethod(interview.getInterviewMethod().getInterviewMethodKoreanName())
        .interviewType(interview.getInterviewType().getInterviewTypeKoreanName())
        .rewardAmount(interview.getRewardAmount())
        .contactName(interview.getContactName())
        .contactPhone(interview.getContactPhone())
        .contactEmail(interview.getContactEmail())
        .regDate(interview.getRegDate())
        .modifiedDate(interview.getModifiedDate())
        .thumbnailUrl(interview.getThumbnailUrl())
        .region(interview.getRegion().getKoreanRegionName())
        .build();
  }
}
