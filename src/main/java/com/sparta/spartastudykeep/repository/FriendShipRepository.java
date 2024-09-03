package com.sparta.spartastudykeep.repository;

import com.sparta.spartastudykeep.entity.Friendship;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendShipRepository extends JpaRepository<Friendship, Long> {
    void deleteByUserIdAndFriendId(Long userId, Long friendId);
    Optional<Friendship> findByUserIdAndFriendId(Long userId, Long friendId);

    List<Friendship> findAllByUserIdAndStatus(Long userId, String name);
}
