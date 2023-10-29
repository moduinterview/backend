package com.moduinterview.common.controller;

import com.moduinterview.user.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CommonController {

  private final UserServiceImpl userServiceImpl;

  @GetMapping("/home")
  public String home() {
    return "home";
  }


}
