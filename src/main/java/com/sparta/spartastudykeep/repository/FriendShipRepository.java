package com.sparta.spartastudykeep.repository;

import com.sparta.spartastudykeep.common.enums.FriendShipStatus;
import com.sparta.spartastudykeep.entity.Friendship;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendShipRepository extends JpaRepository<Friendship, Long> {
    void deleteByRequesterIdAndReceiverId(Long requesterId, Long receiverId);
    Optional<Friendship> findByRequesterIdAndReceiverId(Long requesterId, Long receiverId);
    List<Friendship> findAllByReceiverIdAndStatus(Long receiver_id, FriendShipStatus status);
}
