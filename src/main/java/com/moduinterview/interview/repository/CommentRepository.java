package com.moduinterview.interview.repository;


import com.moduinterview.interview.entity.Comment;
import com.moduinterview.interview.entity.Interview;
import com.moduinterview.user.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {


  boolean existsByInterviewAndWriterAndIsAnswerAndIsDeleted(Interview interview, User user, boolean b, boolean b1);
  Optional<Comment> findTopByInterviewOrderByParentOrderDesc(Interview interview);
  Optional<Comment> findTopByInterviewAndParentOrder(Interview interview, int parentOrder);
  List<Comment> findByInterviewIdAndIsDeletedOrderByParentOrderAscChildOrderAsc (Long interviewId, boolean isDeleted);

}
