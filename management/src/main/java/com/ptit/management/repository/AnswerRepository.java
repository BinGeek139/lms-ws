package com.ptit.management.repository;

import com.ptit.management.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer,String> {
}
