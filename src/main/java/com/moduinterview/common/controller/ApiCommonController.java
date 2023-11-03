package com.moduinterview.common.controller;

import com.moduinterview.common.model.ResponseResult;
import com.moduinterview.interview.dto.CommentResponseDto;
import com.moduinterview.interview.dto.InterviewListResponseDto;
import com.moduinterview.interview.dto.InterviewResult;
import com.moduinterview.interview.enums.InterviewType;
import com.moduinterview.interview.service.CommentService;
import com.moduinterview.interview.service.InterviewService;
import com.moduinterview.user.dto.FindPasswordRequestDto;
import com.moduinterview.user.service.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api-common")
public class ApiCommonController {

  private final UserServiceImpl userServiceImpl;
  private final InterviewService userInterviewService;
  private final CommentService commentService;


  @Operation(summary = "댓글 조회", description = "인터뷰에 등록된 모든 댓글 조회.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "OK",
          content = @Content(schema = @Schema(implementation = CommentResponseDto.class))),
      @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
      @ApiResponse(responseCode = "404", description = "NOT FOUND"),
      @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
  })

  @GetMapping("/comment/{InterviewId}")
  public ResponseEntity<?> getComment(
      @Parameter(description = "인터뷰Id", required = true, example = "1")
      @PathVariable Long InterviewId) {
    return ResponseResult.success(commentService.getComment(InterviewId));
  }


  @Operation(summary = "이메일 인증", description = "메일로 발송된 인증키 링크로 이동시 인증")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "OK"),
      @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
      @ApiResponse(responseCode = "404", description = "NOT FOUND"),
      @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
  })

  //Email Authentication
  @PatchMapping("/emailAuth/{email}/{authKey}")
  public ResponseEntity<?> emailAuth(
      @Parameter(description = "인증키가 발송된 메일주소", required = true, example = "example@gmail.com", in = ParameterIn.PATH) @PathVariable String email,
      @Parameter(description = "해당 메일의 난수", required = true, example = "!@$dsafdf_124124", in = ParameterIn.PATH) @PathVariable String authKey) {
    return ResponseResult.result(userServiceImpl.emailAuth(email, authKey));
  }


  @Operation(summary = "인터뷰 리스트 타입 반환", description = "모든 인터뷰 타입 Enum값 반환")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "OK",
          content = @Content(schema = @Schema(implementation = InterviewType.class))),
      @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
      @ApiResponse(responseCode = "404", description = "NOT FOUND"),
      @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
  })
  @GetMapping("/interviewtype/list")
  public ResponseEntity<?> getAllInterviewTypeList() {
    return ResponseResult.success(userInterviewService.getAllInterviewType());
  }

  @Operation(summary = "인터뷰 타입에 해당하는 인터뷰 리스트 반환", description = "변수에 해당하는 리스트 반환")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "OK",
          content = @Content(schema = @Schema(implementation = InterviewListResponseDto.class))),
      @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
      @ApiResponse(responseCode = "404", description = "NOT FOUND"),
      @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
  })
  @GetMapping("/interviewtype/{interviewType}")
  public ResponseEntity<?> getAllInterviewByTypes(
      @Parameter(description = "인터뷰타입 입력", required = true, example = "PRODUCT_UT")
      @PathVariable String interviewType) {
    return ResponseResult.success(
        userInterviewService.getAllinterviewsByType(InterviewType.valueOf(interviewType)));
  }

  @Operation(summary = "모든 인터뷰 리스트 반환", description = "모든 인터뷰 리스트값 반환")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "OK",
          content = @Content(schema = @Schema(implementation = InterviewListResponseDto.class))),
      @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
      @ApiResponse(responseCode = "404", description = "NOT FOUND"),
      @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
  })

  @GetMapping("/interview/all")
  public ResponseEntity<?> getAllInterviews() {
    return ResponseResult.success(userInterviewService.getAllInterviews());
  }


  @Operation(summary = "개별 인터뷰 상세정보", description = "개별 인터뷰 상세정보 반환")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "OK",
          content = @Content(schema = @Schema(implementation = InterviewResult.class))),
      @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
      @ApiResponse(responseCode = "404", description = "NOT FOUND"),
      @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
  })

  @GetMapping("/interview/{interviewId}")
  public ResponseEntity<?> getAInterview(@PathVariable Long interviewId) {
    return ResponseResult.success(userInterviewService.getAInterview(interviewId));
  }


  @Operation(summary = "비밀번호 찾기", description = "메일, 휴대폰번호로 비밀번호 찾기")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "OK, 수정된 비밀번호가 메일로 발송됨."),
      @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
      @ApiResponse(responseCode = "404", description = "NOT FOUND"),
      @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
  })

  @PostMapping("/find-password")
  public ResponseEntity<?> findPassword(@RequestBody FindPasswordRequestDto findPasswordRequestDto) {
    return ResponseResult.success(userServiceImpl.findPassword(findPasswordRequestDto));
  }


}
