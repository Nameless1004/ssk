package com.sparta.spartastudykeep.service;

import com.sparta.spartastudykeep.common.enums.FriendShipStatus;
import com.sparta.spartastudykeep.entity.Friendship;
import com.sparta.spartastudykeep.entity.User;
import com.sparta.spartastudykeep.repository.FriendShipRepository;
import com.sparta.spartastudykeep.repository.UserRepository;
import com.sparta.spartastudykeep.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class FriendshipService {

    private final UserRepository userRepository;
    private final FriendShipRepository friendShipRepository;

    public void requestFriendship(User user, Long receiverId) {
        User receiver = getUserOrElseThrow(receiverId);
        Friendship friendship = new Friendship(user, receiver);
        friendShipRepository.save(friendship);
    }

    public void rejectFriendshipRequest(User user, Long rejectedUserId) {
        friendShipRepository.deleteByRequesterIdAndReceiverId(rejectedUserId, user.getId());
    }

    public void acceptFriendShip(User user, Long requestUserId) {
        Friendship request = friendShipRepository.findByRequesterIdAndReceiverId(requestUserId,
                user.getId())
            .orElseThrow(() -> new IllegalArgumentException("수락할 요청이 없습니다."));

        User requester = request.getRequester();
        Friendship receive = new Friendship(user, requester);

        // 서로 요청 상태 accepted로 변경
        request.accept();
        receive.accept();

        // 서로 연결
        friendShipRepository.save(receive);
    }

    @Transactional(readOnly = true)
    public void getFriendAll(User user) {
        friendShipRepository.findAllByReceiverIdAndStatus(user.getId(), FriendShipStatus.ACCEPTED.name());
    }

    public void removeFriendship(User user, Long removeUserId) {
        User removeUser = getUserOrElseThrow(removeUserId);
        friendShipRepository.deleteByRequesterIdAndReceiverId(user.getId(), removeUser.getId());
        friendShipRepository.deleteByRequesterIdAndReceiverId(removeUser.getId(), user.getId());
    }

    private User getUserOrElseThrow(Long id){
        return userRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아이디입니다."));
    }
}
