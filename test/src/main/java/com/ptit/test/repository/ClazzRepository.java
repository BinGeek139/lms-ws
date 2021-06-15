package com.ptit.test.repository;

import com.ptit.test.entity.Clazz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ClazzRepository extends JpaRepository<Clazz, String> {
    List<Clazz> findByCode(String code);

    @Query("SELECT c FROM Clazz c JOIN FETCH c.exams WHERE c.id = (:id)")
    Optional<Clazz> findByIdAndAndFetchExamsEagerly(String id);


}
