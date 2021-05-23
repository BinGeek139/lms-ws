package com.ptit.test.repository;

import com.ptit.test.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question,String> {
}
