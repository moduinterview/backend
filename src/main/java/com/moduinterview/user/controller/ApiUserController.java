package com.moduinterview.user.controller;

import com.moduinterview.common.model.ResponseResult;
import com.moduinterview.user.repository.UserRepository;
import com.moduinterview.user.service.LoginServiceImpl;
import com.moduinterview.user.service.UserServiceImpl;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api-user")
public class ApiUserController {

  private final UserRepository userRepository;
  private final UserServiceImpl userServiceImpl;

  private final LoginServiceImpl logintService;


  @GetMapping("/test")
  public String test() {
    return " Url got accessed";
  }


  //Email Authentication
  @PatchMapping("/emailAuth/{email}/{authKey}")
  public ResponseEntity<?> emailAuth(@PathVariable String email, @PathVariable String authKey) {
    return ResponseResult.result(userServiceImpl.emailAuth(email, authKey));
  }


  // Member Delete
  /*
   *회원정보는 삭제하지 않고 우선 Status를 Deleted로 처리한다.
   */
  @PatchMapping("/delete")
  public ResponseEntity<?> deleteUser(HttpServletRequest request) {
    return ResponseResult.result(userServiceImpl.deleteUser(request));
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
