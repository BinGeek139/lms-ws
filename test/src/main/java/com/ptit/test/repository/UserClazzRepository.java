package com.ptit.test.repository;

import com.ptit.test.entity.UserClazz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserClazzRepository extends JpaRepository<UserClazz,String> {
}
