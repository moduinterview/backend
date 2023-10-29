//package com.moduinterview.common;
//
//import com.moduinterview.interview.enums.InterviewCategory;
//import com.moduinterview.interview.enums.InterviewMethod;
//import com.moduinterview.interview.enums.InterviewStatus;
//import com.moduinterview.interview.enums.InterviewType;
//import com.moduinterview.interview.enums.Region;
//import com.moduinterview.interview.enums.TimeUnit;
//import java.util.ArrayList;
//import java.util.List;
//import lombok.Data;
//import lombok.Getter;
//import org.springframework.stereotype.Service;
//
//@Service
//@Getter
//@Data
//public class UtilityService {
//
//  @Getter
//  private   List<Enum[]> enums = new ArrayList<>();
//
//
//  public static UtilityService getInstance() {
//    return new UtilityService();
//  }
//
//
//  public void getAllEnums() {
//    enums.add(InterviewCategory.values());
//    enums.add(InterviewMethod.values());
//    enums.add(InterviewStatus.values());
//    enums.add(InterviewType.values());
//    enums.add(Region.values());
//    enums.add(TimeUnit.values());
//  }
//
//
//}
