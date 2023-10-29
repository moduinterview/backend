package com.moduinterview.interview.controller;


import com.moduinterview.common.model.ResponseError;
import com.moduinterview.common.model.ResponseResult;
import com.moduinterview.interview.dto.CommentInput;
import com.moduinterview.interview.service.CommentService;
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
@RequestMapping(value = "/api-user-comment")
public class ApiUserCommentController {

  private final CommentService commentService;
  private final UserServiceImpl userServiceImpl;

  @PostMapping("/create/{interviewId}")
  public ResponseEntity<?> createComment(HttpServletRequest request, @RequestBody @Valid
  CommentInput commentInput, Errors errors, @PathVariable Long interviewId) {

    if (errors.hasErrors()) {
      List<ResponseError> responseErrorList = new ArrayList<>();
      errors.getAllErrors().forEach(e -> responseErrorList.add(ResponseError.of((FieldError) e)));
      return ResponseResult.fail("댓글 생성에 실패하였습니다. "
          + "올바른 값을 입력해주세요.", responseErrorList);
    }
    User user = userServiceImpl.findUserFromRequest(request);

    return ResponseResult.result(commentService.createComment(commentInput, user, interviewId));
  }

  //TODO: 전체 사용 메서드로 변경 필요.
  @GetMapping("/comment/{InterviewId}")
  public ResponseEntity<?> getComment(@PathVariable Long InterviewId) {
    return ResponseResult.success(commentService.getComment(InterviewId));
  }

  @PatchMapping("/update/{commentId}")
  public ResponseEntity<?> createInterview(HttpServletRequest request, @RequestBody @Valid
  CommentInput commentInput, Errors errors, @PathVariable Long commentId) {

    if (errors.hasErrors()) {
      List<ResponseError> responseErrorList = new ArrayList<>();
      errors.getAllErrors().forEach(e -> responseErrorList.add(ResponseError.of((FieldError) e)));
      return ResponseResult.fail("인터뷰 수정에 실패하였습니다. "
          + "올바른 값을 입력해주세요.", responseErrorList);
    }

    User user = userServiceImpl.findUserFromRequest(request);

    return ResponseResult.result(commentService.updateComment(commentInput, user, commentId));
  }

  @PatchMapping("/delete/{commentId}")
  public ResponseEntity<?> createInterview(HttpServletRequest request,
      @PathVariable Long commentId) {

    User user = userServiceImpl.findUserFromRequest(request);

    return ResponseResult.result(commentService.deleteComment(commentId, user));
  }


}


