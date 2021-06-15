package com.ptit.test.repository;

import com.ptit.test.entity.Exam;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExamRepository extends JpaRepository<Exam, String> {
    List<Exam> findByCode(String code);

    List<Exam> findByClazz_Id(String idClazz);
}
