package com.ptit.test.repository;

import com.ptit.test.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer,String> {
}
