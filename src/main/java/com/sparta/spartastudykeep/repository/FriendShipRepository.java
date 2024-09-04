package com.sparta.spartastudykeep.repository;

import com.sparta.spartastudykeep.common.enums.FriendShipStatus;
import com.sparta.spartastudykeep.entity.Friendship;
import com.sparta.spartastudykeep.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendShipRepository extends JpaRepository<Friendship, Long> {

    void deleteByRequesterAndReceiver(User requester, User receiver);

    void deleteAllByRequesterOrReceiver(User l, User r);

    Optional<Friendship> findByRequesterAndReceiver(User requester, User receiver);

    List<Friendship> findAllByReceiverAndStatus(User user, FriendShipStatus status);

    boolean existsByRequesterAndReceiverAndStatus(User requester, User user,
        FriendShipStatus friendShipStatus);
}
