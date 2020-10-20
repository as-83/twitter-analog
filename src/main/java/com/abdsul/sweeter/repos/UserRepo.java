package com.abdsul.sweeter.repos;

import com.abdsul.sweeter.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
