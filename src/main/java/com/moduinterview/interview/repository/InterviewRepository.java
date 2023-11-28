package com.moduinterview.interview.repository;


import com.moduinterview.interview.entity.Interview;
import com.moduinterview.interview.enums.InterviewStatus;
import com.moduinterview.interview.enums.InterviewType;
import com.moduinterview.user.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface InterviewRepository extends JpaRepository<Interview, Long>,
    JpaSpecificationExecutor<Interview> {

  List<Interview> findAllByUserAndInterviewStatusIs(User user, InterviewStatus interviewStatus);
  List<Interview> findAllByInterviewTypeAndInterviewStatusIs(InterviewType interviewType, InterviewStatus interviewStatus);
  List<Interview> findAllByInterviewStatusIs(InterviewStatus interviewStatus);

  Optional<Interview> findByIdAndInterviewStatusIs(Long interviewId, InterviewStatus interviewStatus );

}
