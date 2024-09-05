package com.sparta.spartastudykeep.friend.repository;

import com.sparta.spartastudykeep.common.enums.FriendShipStatus;
import com.sparta.spartastudykeep.friend.entity.Friendship;
import com.sparta.spartastudykeep.user.entity.User;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendShipRepository extends JpaRepository<Friendship, Long> {

    void deleteByRequesterAndReceiver(User requester, User receiver);

    void deleteAllByRequesterOrReceiver(User l, User r);

    Optional<Friendship> findByRequesterAndReceiver(User requester, User receiver);

    Set<Friendship> findAllByReceiverAndStatus(User user, FriendShipStatus status);

    boolean existsByRequesterAndReceiverAndStatus(User requester, User receiver, FriendShipStatus friendShipStatus);
}
