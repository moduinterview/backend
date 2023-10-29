package com.moduinterview.interview.controller;


import com.moduinterview.common.model.ResponseError;
import com.moduinterview.common.model.ResponseResult;
import com.moduinterview.interview.dto.InterviewInput;
import com.moduinterview.interview.enums.InterviewType;
import com.moduinterview.interview.service.InterviewService;
import com.moduinterview.user.entity.User;
import com.moduinterview.user.service.UserServiceImpl;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api-user-interview")
public class ApiUserInterviewController {

  private final InterviewService userInterviewService;
  private final UserServiceImpl userServiceImpl;

  @PostMapping("/create")
  public ResponseEntity<?> createInterview(HttpServletRequest request, @RequestBody @Valid
  InterviewInput interviewInput, Errors errors) {

    if (errors.hasErrors()) {
      List<ResponseError> responseErrorList = new ArrayList<>();
      errors.getAllErrors().forEach(e -> responseErrorList.add(ResponseError.of((FieldError) e)));
      return ResponseResult.fail("인터뷰 생성에 실패하였습니다. "
          + "올바른 값을 입력해주세요.", responseErrorList);
    }
    User user = userServiceImpl.findUserFromRequest(request);

    return ResponseResult.result(userInterviewService.createInterview(interviewInput, user));
  }

  @PatchMapping("/update/{interviewId}")
  public ResponseEntity<?> createInterview(HttpServletRequest request, @RequestBody @Valid
  InterviewInput interviewInput, Errors errors, @PathVariable Long interviewId) {

    if (errors.hasErrors()) {
      List<ResponseError> responseErrorList = new ArrayList<>();
      errors.getAllErrors().forEach(e -> responseErrorList.add(ResponseError.of((FieldError) e)));
      return ResponseResult.fail("인터뷰 수정에 실패하였습니다. "
          + "올바른 값을 입력해주세요.", responseErrorList);
    }
    User user = userServiceImpl.findUserFromRequest(request);

    return ResponseResult.result(
        userInterviewService.updateInterview(interviewInput, user, interviewId));
  }

  //TODO: 전체 사용 메서드로 변경 필요.
  @GetMapping("/interviewtype/list")
  public ResponseEntity<?> getAllInterviewTypeList() {
    return ResponseResult.success(userInterviewService.getAllInterviewType());
  }

  @GetMapping("/interviewtype/{interviewType}")
  public ResponseEntity<?> getAllInterviewTypes(@PathVariable String interviewType) {
    return ResponseResult.success(
        userInterviewService.getAllinterviewsByType(InterviewType.valueOf(interviewType)));
  }
  @GetMapping("/interview/all")
  public ResponseEntity<?> getAllInterviews() {
    return ResponseResult.success(userInterviewService.getAllInterviews());
  }
  @GetMapping("/interview/{interviewId}")
  public ResponseEntity<?> getAInterview(@PathVariable Long interviewId) {
    return ResponseResult.success(userInterviewService.getAInterview(interviewId));
  }



}


