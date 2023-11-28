package com.moduinterview.interview.repository;

import com.moduinterview.interview.entity.Interview;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class InterviewSpecification implements Specification<Interview> {

  @Override
  public Predicate toPredicate(Root<Interview> root, CriteriaQuery<?> query,
      CriteriaBuilder criteriaBuilder) {
    return null;
  }
}
