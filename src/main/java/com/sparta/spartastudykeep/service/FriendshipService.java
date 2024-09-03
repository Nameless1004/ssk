package com.sparta.spartastudykeep.service;

import com.sparta.spartastudykeep.common.enums.FriendShipStatus;
import com.sparta.spartastudykeep.entity.Friendship;
import com.sparta.spartastudykeep.entity.User;
import com.sparta.spartastudykeep.repository.FriendShipRepository;
import com.sparta.spartastudykeep.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class FriendshipService {

    private final UserRepository userRepository;
    private final FriendShipRepository friendShipRepository;

    public void requestFriendship(Long fromUserId, Long toUserId) {
        User from = getUserOrElseThrow(fromUserId);
        User to = getUserOrElseThrow(toUserId);
        Friendship friendship = new Friendship(from, to);
        friendShipRepository.save(friendship);
    }

    public void rejectFriendshipRequest(Long userId, Long rejectedUserId) {
        friendShipRepository.deleteByUserIdAndFriendId(rejectedUserId, userId);
    }

    private User getUserOrElseThrow(Long id){
        return userRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아이디입니다."));
    }

    public void acceptFriendShip(Long userId, Long acceptedFriendId) {
        Friendship request = friendShipRepository.findByUserIdAndFriendId(acceptedFriendId,
                userId)
            .orElseThrow(() -> new IllegalArgumentException("수락할 요청이 없습니다."));

        User acceptedUser = request.getUser();
        User user = request.getFriend();
        Friendship connection = new Friendship(user, acceptedUser);

        // 서로 요청 상태 accepted로 변경
        request.accept();
        connection.accept();

        // 서로 연결
        friendShipRepository.save(connection);
    }

    public void getFriendAll(Long userId) {
        friendShipRepository.findAllByUserIdAndStatus(userId, FriendShipStatus.ACCEPTED.name());
    }

    public void removeFriendship(Long userId, Long acceptedFriendId) {

    }
}
