package com.ptit.test.repository;

import com.ptit.test.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question,String> {

    List<Question> findByExam_Id(String id);
}
