package com.moduinterview.user.controller;

import com.moduinterview.common.dto.SignUpRequestDto;
import com.moduinterview.common.model.ResponseError;
import com.moduinterview.common.model.ResponseResult;
import com.moduinterview.common.model.ServiceResult;
import com.moduinterview.user.component.JwtTokenProvider;
import com.moduinterview.user.dto.LoginRequestDto;
import com.moduinterview.user.dto.SignUpInput;
import com.moduinterview.user.dto.TokenResponseDto;
import com.moduinterview.user.service.LoginServiceImpl;
import com.moduinterview.user.service.UserServiceImpl;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ApiLoginController {

  private final UserServiceImpl userServiceImpl;

  private final LoginServiceImpl logintService;
  private final JwtTokenProvider jwtTokenProvider;

  @PostMapping("/signup")
  public ResponseEntity<?> signUp(@RequestBody @Valid SignUpRequestDto requestDto, Errors errors) {

    if (errors.hasErrors()) {
      List<ResponseError> responseErrorList = new ArrayList<>();
      errors.getAllErrors().forEach(e -> responseErrorList.add(ResponseError.of((FieldError) e)));
      return ResponseResult.fail("회원가입에 실패하였습니다.", responseErrorList);
    }

    return ResponseResult.result(userServiceImpl.signUp(requestDto));
  }

  @GetMapping("/login")
  public ResponseEntity<?> signIn(@RequestBody @Valid LoginRequestDto requestDto, Errors errors) {
    if (errors.hasErrors()) {
      List<ResponseError> responseErrorList = new ArrayList<>();
      errors.getAllErrors().forEach(e -> responseErrorList.add(ResponseError.of((FieldError) e)));
      return ResponseResult.fail("로그인에 실패하였습니다.", responseErrorList);
    }
    TokenResponseDto tokenDto = jwtTokenProvider.createAccessToken(
        logintService.getUserIdFromAuthentication(requestDto));

    return ResponseResult.result(ServiceResult.success("로그인에 성공하였습니다.", tokenDto));
  }

  // 로그아웃
  @GetMapping("/signout")
  public ResponseEntity<?> signOut(@RequestBody @Valid SignUpInput signUpInput, Errors errors) {
    return null;
  }
  // Member List

//  // Member Detail
//  @GetMapping("/user/detail/{id}")
//  public ResponseEntity<?> detailUser(@PathVariable String email) {
//    ServiceResult serviceResult = userService.getUserDetail(email);
//
//    return ResponseResult.result(serviceResult);
//  }


}
