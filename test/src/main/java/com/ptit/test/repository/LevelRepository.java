package com.ptit.test.repository;

import com.ptit.test.entity.Level;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LevelRepository extends JpaRepository<Level,String> {
}
