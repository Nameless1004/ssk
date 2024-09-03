package com.sparta.spartastudykeep.repository;

import com.sparta.spartastudykeep.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
