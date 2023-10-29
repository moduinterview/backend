package com.moduinterview.interview.enums;


public enum InterviewMethod {
  F2F("대면"),
  PHONE("유선"),
  VIDEO("화상"),
  SURVEY("설문"),
  ETC("기타");

  private final String interviewMethodKoreanName;

  InterviewMethod(String interviewTypeKoreanName) {
    this.interviewMethodKoreanName = interviewTypeKoreanName;
  }

  public String getInterviewMethodKoreanName() {
    return interviewMethodKoreanName;
  }
}
