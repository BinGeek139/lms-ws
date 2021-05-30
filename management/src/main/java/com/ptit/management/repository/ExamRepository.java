package com.ptit.management.repository;

import com.ptit.management.entity.Exam;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExamRepository extends JpaRepository<Exam, String> {
    List<Exam> findByCode(String code);
    List<Exam> findByClazz_IdTeacher(String id);
    List<Exam> findByClazz_Id(String id);

}
