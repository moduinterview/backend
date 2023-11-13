package com.moduinterview.interview.service;

import com.moduinterview.common.model.ServiceResult;
import com.moduinterview.interview.dto.InterviewListResponseDto;
import com.moduinterview.interview.dto.InterviewInput;
import com.moduinterview.interview.dto.InterviewResult;
import com.moduinterview.interview.entity.Interview;
import com.moduinterview.interview.enums.InterviewStatus;
import com.moduinterview.interview.enums.InterviewType;
import com.moduinterview.interview.exception.InterviewNotFoundException;
import com.moduinterview.interview.repository.InterviewRepository;
import com.moduinterview.user.entity.User;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InterviewServiceImpl implements InterviewService {

  private final InterviewRepository interviewRepository;

  @Override
  @Transactional
  public ServiceResult createInterview(InterviewInput interviewInput, User user) {
    if (!validateInterviewInput(interviewInput)) {
      return ServiceResult.fail("입력값이 잘못되었습니다.");
    }
    Interview interview = Interview.createInterviewFrom(interviewInput, user);
    interviewRepository.save(interview);
    return ServiceResult.success("인터뷰가 생성되었습니다.");
  }

  @Override
  @Transactional
  public ServiceResult updateInterview(InterviewInput interviewInput, User user,
      Long interviewId) {
    if (!validateInterviewInput(interviewInput)) {
      return ServiceResult.fail("입력값이 잘못되었습니다.");
    }

    Interview interview = interviewRepository.findById(interviewId)
        .orElseThrow(() -> new IllegalArgumentException("해당 인터뷰가 없습니다."));

    if (user.getId() != interview.getUser().getId()) {
      return ServiceResult.fail("인터뷰를 수정할 권한이 없습니다.");
    }
    interview.setInterviewFrom(interviewInput);
    interviewRepository.save(interview);

    return ServiceResult.success("인터뷰가 수정되었습니다.");
  }

  @Override
  @Transactional
  public ServiceResult deleteInterview(Long InterviewId, User user) {
    Interview interview = interviewRepository.findById(InterviewId)
        .orElseThrow(() -> new IllegalArgumentException("해당 인터뷰가 없습니다."));
    if (user.getId() != interview.getUser().getId()) {
      return ServiceResult.fail("인터뷰를 삭제할 권한이 없습니다.");
    }
    if (interview.getInterviewStatus() == InterviewStatus.DELETED) {
      return ServiceResult.fail("이미 삭제된 인터뷰입니다.");
    }
    interview.setInterviewStatus(InterviewStatus.DELETED);
    interview.setModifiedDate(LocalDateTime.now());
    interviewRepository.save(interview);

    return ServiceResult.success("인터뷰가 삭제상태로 변경되었습니다.");
  }

  @Override
  public List<InterviewType> getAllInterviewType() {
    InterviewType[] values = InterviewType.values();
    return new ArrayList<>(Arrays.asList(values));
  }

  @Override
  public ServiceResult getMyInterviewList(User user) {
    List<Interview> interviewList = interviewRepository.findAllByUserAndInterviewStatusIs(user,
        InterviewStatus.RECRUITING);
    if (interviewList.isEmpty()) {
      return ServiceResult.fail("해당 사용자가 작성한 인터뷰가 없습니다.");
    }
    List<InterviewListResponseDto> interviewListDtoListResponse = new ArrayList<>();
    interviewList.forEach(interview -> interviewListDtoListResponse.add(
        InterviewListResponseDto.createInterviewForListDtoFrom(interview)));
    return ServiceResult.success(interviewListDtoListResponse);
  }

  @Override
  public ServiceResult getAllinterviewsByType(InterviewType interviewType) {
    List<Interview> interviewList = interviewRepository.findAllByInterviewTypeAndInterviewStatusIs(
        interviewType, InterviewStatus.RECRUITING);
    if (interviewList.isEmpty()) {
      return ServiceResult.success("해당 타입의 인터뷰가 없습니다.");
    }
    List<InterviewListResponseDto> interviewListDtoListResponse = new ArrayList<>();
    interviewList.forEach(interview -> interviewListDtoListResponse.add(
        InterviewListResponseDto.createInterviewForListDtoFrom(interview)));

    return ServiceResult.success(interviewListDtoListResponse);
  }

  @Override
  public ServiceResult getAllInterviews() {
    List<Interview> interviewList = interviewRepository.findAllByInterviewStatusIs(
        InterviewStatus.RECRUITING);
    if (interviewList.isEmpty()) {
      return ServiceResult.success("해당 타입의 인터뷰가 없습니다.");
    }
    List<InterviewListResponseDto> interviewListDtoListResponse = new ArrayList<>();
    interviewList.forEach(interview -> interviewListDtoListResponse.add(
        InterviewListResponseDto.createInterviewForListDtoFrom(interview)));
    return ServiceResult.success(interviewListDtoListResponse);
  }

  @Override
  public ServiceResult getAInterview(Long interviewId) {
    Interview interview = interviewRepository.findByIdAndInterviewStatusIs(interviewId,
            InterviewStatus.RECRUITING)
        .orElseThrow(() -> new InterviewNotFoundException("해당 인터뷰가 없거나 삭제되었습니다."));

    InterviewResult interviewResult = InterviewResult.from(interview);

    return ServiceResult.success(interviewResult);
  }


  @Override
  public ServiceResult increaseInterviewHits(Long interviewId, Long userId) {
    return null;
  }

  @Override
  public Long InterviewHits(Long interviewId) {
    return null;
  }

  @Override
  public ServiceResult InterviewLike(Long interviewId, Long userId) {
    return null;
  }

  @Override
  public ServiceResult InterviewUnlike(Long interviewId, Long userId) {
    return null;
  }

  @Override
  public ServiceResult getInterviewLike(Long interviewId, Long userId) {
    return null;
  }

  @Override
  public ServiceResult scrap(Long interviewId, Long userId) {
    return null;
  }

  @Override
  public ServiceResult unscrap(Long interviewId, Long userId) {
    return null;
  }

  @Override
  public Boolean isScrap(Long interviewId, Long userId) {
    return null;
  }

  @Override
  public Boolean isLiked(Long interviewId, Long userId) {
    return null;
  }

  @Override
  public Long getInterviewHits(Long interviewId, Long userId) {
    return null;
  }

  @Override
  public Long getInterviewLike(Long interviewId) {
    return null;
  }

  @Override
  public Long getUserIdFromRequest(HttpServletRequest request) {
    String userId = request.getAttribute("userId").toString();
    if (userId == null) {
      return null;
    }
    return Long.parseLong(userId);
  }

  public boolean validateInterviewInput(InterviewInput input) {
    LocalDate now = LocalDate.now();
    LocalDate startDate = input.getStartDate();
    LocalDate dueDate = input.getDueDate();
    boolean isAgreed = input.getPrivateInfoAgreement();

    return !startDate.isBefore(now) && !dueDate.isBefore(startDate) && isAgreed;

  }
}
