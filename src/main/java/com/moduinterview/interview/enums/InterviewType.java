package com.moduinterview.interview.enums;

public enum InterviewType {
  USER_UT("유저사용성테스트"),
  PRODUCT_UT("제품사용성테스트"),
  SIMPLE_INTERVIEW("단순질의인터뷰"),
  SURVEY("설문조사"),
  MEDICAL_TEST("임상실험"),
  MEDIA_PANEL("방송인터뷰이모집"),
  REVIEWER("리뷰어모집"),
  FOCUS_GROUP("포커스그룹"),
  ETC("기타");

  private final String interviewTypeKoreanName;

  InterviewType(String interviewTypeKoreanName) {
    this.interviewTypeKoreanName = interviewTypeKoreanName;
  }
  public String getInterviewTypeKoreanName() {
    return interviewTypeKoreanName;
  }
}
