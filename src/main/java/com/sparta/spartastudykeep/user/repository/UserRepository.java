package com.sparta.spartastudykeep.user.repository;

import com.sparta.spartastudykeep.common.exception.InvalidIdException;
import com.sparta.spartastudykeep.user.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    default User getUserOrElseThrow(Long id){
        return findById(id).orElseThrow(()->new InvalidIdException("해당 아이디의 유저를 찾을 수 없습니다."));
    }
}
