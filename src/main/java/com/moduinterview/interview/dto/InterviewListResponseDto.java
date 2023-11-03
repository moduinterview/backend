package com.moduinterview.interview.dto;

import com.moduinterview.interview.entity.Interview;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import lombok.Data;

@Data
@Schema(description = "인터뷰 리스트 조회용 DTO")
public class InterviewListResponseDto {

  @Schema(description = "인터뷰 ID")
  public Long interviewId;
  @Schema(description = "인터뷰 제목")
  public String title;
  @Schema(description = "인터뷰 방식")
  public String interviewMethod;
  @Schema(description = "인터뷰 보상")
  public Long rewardAmount;
  @Schema(description = "인터뷰 지역")
  public String region;
  @Schema(description = "인터뷰 등록일")
  public LocalDate regDate;
  @Schema(description = "인터뷰 썸네일 URL")
  public String thumbnailUrl;

  public static InterviewListResponseDto createInterviewForListDtoFrom(Interview interview) {
    InterviewListResponseDto interviewListResponseDto = new InterviewListResponseDto();
    interviewListResponseDto.setInterviewId(interview.getId());
    interviewListResponseDto.setTitle(interview.getTitle());
    interviewListResponseDto.setInterviewMethod(interview.getInterviewMethod().getInterviewMethodKoreanName());
    interviewListResponseDto.setRewardAmount(interview.getRewardAmount());
    interviewListResponseDto.setRegion(interview.getRegion().getKoreanRegionName());
    interviewListResponseDto.setRegDate(interview.getRegDate().toLocalDate());
    interviewListResponseDto.setThumbnailUrl(interview.getThumbnailUrl());
    return interviewListResponseDto;
  }
}
