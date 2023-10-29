package com.moduinterview.interview.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.moduinterview.interview.enums.InterviewMethod;
import com.moduinterview.interview.enums.InterviewType;
import com.moduinterview.interview.enums.Region;
import com.moduinterview.interview.enums.TimeUnit;
import java.time.LocalDate;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class InterviewInput {

  private Integer recruitNum;

  @Length(min = 1, max = 30, message = "제목은 1~30자 길이로 작성해주세요.")
  private String title;

  @JsonDeserialize(using = LocalDateDeserializer.class)
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd")
  private LocalDate startDate;
  @JsonDeserialize(using = LocalDateDeserializer.class)
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd")
  private LocalDate dueDate;
  @JsonDeserialize(using = LocalDateDeserializer.class)
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd")
  private LocalDate interviewDate;

  private Integer duration;
  @Enumerated(value = javax.persistence.EnumType.STRING)
  private TimeUnit durationTimeUnit;

  private String description;

  @Enumerated(value = javax.persistence.EnumType.STRING)
  private InterviewMethod interviewMethod;

  @Enumerated(value = javax.persistence.EnumType.STRING)
  private InterviewType interviewType;


  private Long rewardAmount;


  private String contactName;
  private String contactPhone;

  @Email(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$", message = "이메일 형식이 아닙니다.")
  @NotBlank(message = "이메일을 입력해주세요.")
  private String contactEmail;

  private String thumbnailUrl;

  @Enumerated(value = javax.persistence.EnumType.STRING)
  private Region region;

  private Boolean privateInfoAgreement;

}
