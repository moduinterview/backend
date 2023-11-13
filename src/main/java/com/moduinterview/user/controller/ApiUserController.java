package com.moduinterview.user.controller;

import com.moduinterview.common.model.ResponseResult;
import com.moduinterview.common.model.ServiceResult;
import com.moduinterview.user.dto.AwsS3Response;
import com.moduinterview.user.dto.PasswordUpdateInput;
import com.moduinterview.user.dto.UpdateUserRequestDto;
import com.moduinterview.user.dto.UserResponse;
import com.moduinterview.user.entity.User;
import com.moduinterview.user.service.AwsS3Service;
import com.moduinterview.user.service.UserInfoResponseDto;
import com.moduinterview.user.service.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api-user")
public class ApiUserController {

  private final UserServiceImpl userServiceImpl;
  private final AwsS3Service awsS3Service;

  @Operation(summary = "유저삭제", description = "유저상태를 삭제로 변경(실제 삭제하지 않음)")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "OK"),
      @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
      @ApiResponse(responseCode = "404", description = "NOT FOUND"),
      @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
  })

  // Member Delete
  /*
   *회원정보는 삭제하지 않고 우선 Status를 Deleted로 처리한다.
   */
  @PatchMapping("/delete")
  public ResponseEntity<?> deleteUser(HttpServletRequest request) {
    return ResponseResult.result(userServiceImpl.deleteUser(request));
  }

  @Operation(summary = "유저정보조회", description = "유저상세정보 페이지 결과 조회")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "OK",
          content = @Content(schema = @Schema(implementation = UserInfoResponseDto.class))),
      @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
      @ApiResponse(responseCode = "404", description = "NOT FOUND"),
      @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
  })

  // Member Detail
  @GetMapping("/info")
  public ResponseEntity<?> userInfo(HttpServletRequest request) {
    ServiceResult serviceResult = userServiceImpl.detailUserInfo(request);
    return ResponseResult.result(serviceResult);
  }

  @Operation(summary = "비밀번호 변경", description = "비밀번호 변경요청")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "OK"),
      @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
      @ApiResponse(responseCode = "404", description = "NOT FOUND"),
      @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
  })

  @PatchMapping("/update-password")
  public ResponseEntity<?> updatePassword(HttpServletRequest request,
      @RequestBody PasswordUpdateInput input) {
    ServiceResult serviceResult = userServiceImpl.updatePassword(request, input);
    return ResponseResult.result(serviceResult);
  }

  @Operation(summary = "유저 업데이트", description = "유저 정보 변경")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "OK",
          content = @Content(schema = @Schema(implementation = UserResponse.class))),
      @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
      @ApiResponse(responseCode = "404", description = "NOT FOUND"),
      @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
  })

  @PatchMapping("/updateUser")
  public ResponseEntity<?> updateUser(HttpServletRequest request,
      @RequestBody UpdateUserRequestDto requestDto) {
    ServiceResult serviceResult = userServiceImpl.updateUser(request, requestDto);
    return ResponseResult.result(serviceResult);
  }

  @Operation(summary = "회원탈퇴", description = "회원상태를 탈퇴로 변경")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "OK"),
      @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
      @ApiResponse(responseCode = "404", description = "NOT FOUND"),
      @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
  })

  @PatchMapping("/withdrawal")
  public ResponseEntity<?> withdrawal(HttpServletRequest request) {
    ServiceResult serviceResult = userServiceImpl.withdrawal(request);
    return ResponseResult.result(serviceResult);
  }

  @Operation(summary = "유저 프로필 이미지 등록", description = "유저프로필 이미지 등록/수정. 수정시 기존 이미지 삭제")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "OK",
          content = @Content(schema = @Schema(implementation = AwsS3Response.class))),
      @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
      @ApiResponse(responseCode = "404", description = "NOT FOUND"),
      @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
  })

  @PostMapping("/profile-image")
  public ResponseEntity<?> profileImage(HttpServletRequest request,
      @RequestBody MultipartFile file) {
    User user = userServiceImpl.findUserFromRequest(request);
    ServiceResult result;
    try {
      result = awsS3Service.uploadImage(file, user.getId());
    } catch (IOException e) {
      e.printStackTrace();
      return ResponseResult.result(ServiceResult.fail("이미지 업로드 실패"));
    }
    if (result == null) {
      return ResponseResult.fail("이미지 업로드 실패");
    }
    AwsS3Response response = (AwsS3Response) result.getData();
    userServiceImpl.updateUserProfileImage(user, response.getUrl());

    return ResponseResult.result(result);
  }

  @Operation(summary = "유저 프로필 이미지 가져오기", description = "유저프로필 이미지 가져오기")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "OK",
          content = @Content(schema = @Schema(implementation = AwsS3Response.class))),
      @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
      @ApiResponse(responseCode = "404", description = "NOT FOUND"),
      @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
  })

  @GetMapping("/profile-image")
  public ResponseEntity<?> profileImage(HttpServletRequest request) {
    User user = userServiceImpl.findUserFromRequest(request);
    ServiceResult result = awsS3Service.getImage(user.getId());
    return ResponseResult.result(result);
  }

}
