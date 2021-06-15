package com.ptit.test.repository;

import com.ptit.test.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AnswerRepository extends JpaRepository<Answer,String> {
    @Query(value = "select * from answer where id = ?1 ",nativeQuery = true)
    Optional<Answer> findByIdString(String s);

    @Query(value = "select is_true from answer where id = ?1 ",nativeQuery = true)
    Integer checkAnswer(String id);

}
