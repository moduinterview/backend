package com.moduinterview.interview.service;

import com.moduinterview.common.model.ServiceResult;
import com.moduinterview.interview.dto.InterviewInput;
import com.moduinterview.interview.enums.InterviewType;
import com.moduinterview.user.entity.User;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

public interface InterviewService {

  ///인터뷰 생성
  ServiceResult createInterview(InterviewInput interviewInput, User user);

  ///인터뷰 수정
  ServiceResult updateInterview(InterviewInput interviewInput, User user,
      Long interviewId);

  ///인터뷰 삭제
  ServiceResult deleteInterview(Long InterviewId, User user);

  ///인터뷰 타입반환 : 모든 인터뷰 타입을 리스트로 반환합니다.
  List<InterviewType> getAllInterviewType();

  ServiceResult getMyInterviewList(User user);

  ServiceResult getAllinterviewsByType(InterviewType interviewType);

  ServiceResult getAllInterviews();

  ServiceResult getAInterview(Long interviewId);

  //인터뷰 조회수 증가 : 인터뷰 조회수 테이블에 인터뷰/사용자 정보를 추가합니다.
  ServiceResult increaseInterviewHits(Long interviewId, Long userId);

  ///인터뷰 조회수: InterviewHits 테이블에서 인터뷰 조회수를 조회합니다.
  Long InterviewHits(Long interviewId);

  ///인터뷰 좋아요: InterviewLike 테이블에 인터뷰/사용자 정보를 추가합니다.
  ServiceResult InterviewLike(Long interviewId, Long userId);

  ///인터뷰 좋아요 취소
  ServiceResult InterviewUnlike(Long interviewId, Long userId);

  ///인터뷰 좋아요 조회(유저별)
  ServiceResult getInterviewLike(Long interviewId, Long userId);

  ///인터뷰 스크랩
  ServiceResult scrap(Long interviewId, Long userId);

  ///인터뷰 스크랩 취소
  ServiceResult unscrap(Long interviewId, Long userId);

  ///인터뷰 스크랩 조회(유저별)
  Boolean isScrap(Long interviewId, Long userId);

  ///인터뷰 좋아요 조회(유저별)
  Boolean isLiked(Long interviewId, Long userId);

  //인터뷰 조회수 조회(유저별)
  Long getInterviewHits(Long interviewId, Long userId);

  //인터뷰 좋아요 조회(전체)
  Long getInterviewLike(Long interviewId);

  //request에서 userId를 가져옵니다.
  Long getUserIdFromRequest(HttpServletRequest request);


}
