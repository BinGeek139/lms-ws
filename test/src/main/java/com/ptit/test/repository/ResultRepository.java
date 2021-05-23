package com.ptit.test.repository;

import com.ptit.test.entity.Result;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResultRepository extends JpaRepository<Result,String> {
}
