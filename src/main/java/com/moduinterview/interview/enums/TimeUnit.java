package com.moduinterview.interview.enums;

public enum TimeUnit {

  MINUTE("분"), DAY("일"), HOUR("시간");

  private final String KoreanTimeUnit;

  TimeUnit(String KoreanTimeUnit) {
    this.KoreanTimeUnit = KoreanTimeUnit;
  }
  public String getKoreanTimeUnit() {
    return KoreanTimeUnit;
  }
}
