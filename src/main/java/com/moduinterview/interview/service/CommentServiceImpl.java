package com.moduinterview.interview.service;

import com.moduinterview.common.model.ServiceResult;
import com.moduinterview.interview.dto.CommentInput;
import com.moduinterview.interview.dto.CommentResponseDto;
import com.moduinterview.interview.entity.Comment;
import com.moduinterview.interview.entity.Interview;
import com.moduinterview.interview.exception.CommentNotFoundException;
import com.moduinterview.interview.exception.InterviewNotFoundException;
import com.moduinterview.interview.repository.CommentRepository;
import com.moduinterview.interview.repository.InterviewRepository;
import com.moduinterview.user.entity.User;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

  private final CommentRepository commentRepository;
  private final InterviewRepository interviewRepository;


  @Override
  public ServiceResult createComment(CommentInput input, User user, Long interviewId) {

    Interview interview = interviewRepository.findById(interviewId)
        .orElseThrow(() -> new InterviewNotFoundException("존재하지 않는 인터뷰입니다."));

    //해당게시물에 이미 해당유저가 작성한 댓글(질문)이 있는지 확인.
    boolean isExist = commentRepository.existsByInterviewAndWriterAndIsAnswerAndIsDeleted(interview,
        user, false, false);
    System.out.println("isExist = " + isExist);
    if (isExist) {
      return ServiceResult.fail("이미 해당 유저가 작성한 댓글이 있습니다.");
    }

    //답변인지 확인
    boolean isAnswer = user.getId() == interview.getUser().getId();
    System.out.println("isAnswer = " + isAnswer);
    //TODO: input에 interviewId를 넣어줄 것인가 아니면 pathVariable로 받을 것인가?
    Comment comment = Comment.createCommmentFrom(input, user, interview);
    comment.setWriter(user);
    comment.setDeleted(isAnswer);

    //parent, child 설정.
    if (input.getParentOrder() == null) {
      Optional<Comment> optionalComment = commentRepository.findTopByInterviewOrderByParentOrderDesc(
          interview);
      if (optionalComment.isPresent()) {
        int parentOrder = optionalComment.get().getParentOrder();
        comment.setParentOrder(parentOrder + 1);
      } else {
        comment.setParentOrder(0);
      }
    }
    if (input.getChildOrder() == null) {
      Optional<Comment> childOrder = commentRepository.findTopByInterviewAndParentOrder(
          interview, comment.getParentOrder());
      if (childOrder.isPresent()) {
        comment.setChildOrder(1);
      } else {
        comment.setChildOrder(0);
      }
    }
    commentRepository.save(comment);

    return ServiceResult.success("댓글 작성에 성공했습니다.");
  }

  @Override
  public ServiceResult updateComment(CommentInput input, User user, Long CommentId) {

    Comment comment = commentRepository.findById(CommentId)
        .orElseThrow(() -> new CommentNotFoundException("존재하지 않는 댓글입니다."));

    if (comment.isDeleted()) {
      return ServiceResult.fail("삭제된 댓글입니다.");
    }
    if (comment.getWriter().getId() != user.getId()) {
      return ServiceResult.fail("해당 댓글의 작성자가 아닙니다.");
    }
    comment.setContent(input.getContent());
    commentRepository.save(comment);

    return ServiceResult.success("댓글 수정에 성공했습니다.");
  }

  @Override
  public ServiceResult deleteComment(Long CommentId, User user) {
    Comment comment = commentRepository.findById(CommentId)
        .orElseThrow(() -> new CommentNotFoundException("존재하지 않는 댓글입니다."));

    if (comment.isDeleted()) {
      return ServiceResult.fail("이미 삭제된 댓글입니다.");
    }
    if (comment.getWriter().getId() != user.getId()) {
      return ServiceResult.fail("해당 댓글의 작성자가 아닙니다.");
    }
    comment.setDeleted(true);
    commentRepository.save(comment);

    return ServiceResult.success("댓글이 삭제되었습니다.");

  }

  @Override
  public ServiceResult getComment(Long interviewId) {

    List<Comment> comments = commentRepository.findByInterviewIdAndIsDeletedOrderByParentOrderAscChildOrderAsc(
        interviewId, false);

    if (comments.size() == 0) {
      return ServiceResult.fail("댓글이 존재하지 않습니다.");
    }
    List<CommentResponseDto> result = comments.stream().map(CommentResponseDto::of)
        .collect(Collectors.toList());

    return ServiceResult.success("댓글 조회에 성공했습니다.", result);
  }
}
