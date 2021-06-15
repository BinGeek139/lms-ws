package com.ptit.test.repository;

import com.ptit.test.entity.UserClazz;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserClazzRepository extends JpaRepository<UserClazz,String> {

    List<UserClazz> findByUser_Id(String id);
}
