package com.moduinterview.interview.service;

import com.moduinterview.common.model.ServiceResult;
import com.moduinterview.interview.dto.CommentInput;
import com.moduinterview.user.entity.User;

public interface CommentService {

  ///Comment생성
  ServiceResult createComment(CommentInput QnaInput, User user, Long interviewId);

  ///Comment수정
  ServiceResult updateComment(CommentInput commentInput, User user, Long commentId);

  ///Comment삭제
  ServiceResult deleteComment(Long commentId, User user);

  //Comment가져오기
  ServiceResult getComment(Long interviewId);

}
