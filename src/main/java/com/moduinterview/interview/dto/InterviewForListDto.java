package com.moduinterview.interview.dto;

import com.moduinterview.interview.entity.Interview;
import java.time.LocalDate;
import lombok.Data;

@Data
public class InterviewForListDto {

  public Long interviewId;
  public String title;
  public String interviewMethod;
  public Long rewardAmount;
  public String region;
  public LocalDate regDate;
  public String thumbnailUrl;

  public static InterviewForListDto createInterviewForListDtoFrom(Interview interview) {
    InterviewForListDto interviewForListDto = new InterviewForListDto();
    interviewForListDto.setInterviewId(interview.getId());
    interviewForListDto.setTitle(interview.getTitle());
    interviewForListDto.setInterviewMethod(interview.getInterviewMethod().getInterviewMethodKoreanName());
    interviewForListDto.setRewardAmount(interview.getRewardAmount());
    interviewForListDto.setRegion(interview.getRegion().getKoreanRegionName());
    interviewForListDto.setRegDate(interview.getRegDate().toLocalDate());
    interviewForListDto.setThumbnailUrl(interview.getThumbnailUrl());
    return interviewForListDto;
  }
}
