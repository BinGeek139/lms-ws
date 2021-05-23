package com.ptit.author.repo;

import com.ptit.author.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User,String> {
    Optional<User> findByUsername(String userName);
}
