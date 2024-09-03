package com.sparta.spartastudykeep.service;

import com.sparta.spartastudykeep.common.enums.FriendShipStatus;
import com.sparta.spartastudykeep.entity.Friendship;
import com.sparta.spartastudykeep.entity.User;
import com.sparta.spartastudykeep.repository.FriendShipRepository;
import com.sparta.spartastudykeep.repository.UserRepository;
import com.sparta.spartastudykeep.security.UserDetailsImpl;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class FriendshipService {

    private final UserRepository userRepository;
    private final FriendShipRepository friendShipRepository;

    /**
     * 친구 요청
     * @param user 현재 로그인 유저
     * @param receiverId 친구요청 받을 유저의 아이디
     */
    public void requestFriendship(User user, Long receiverId) {
        User receiver = getUserOrElseThrow(receiverId);
        Friendship friendship = new Friendship(user, receiver);
        friendShipRepository.save(friendship);
    }

    /**
     * 친구요청 거절
     * @param user 현재 로그인 유저
     * @param rejectedUserId 친구요청 받을 유저의 아이디
     */
    public void rejectFriendshipRequest(User user, Long rejectedUserId) {
        friendShipRepository.deleteByRequesterIdAndReceiverId(rejectedUserId, user.getId());
    }

    /**
     * 친구요청 수락
     * @param user 현재 로그인 유저
     * @param requestUserId 친구요청한 유저의 아이디
     */
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

    /**
     * 친구목록 가져오기, ACCEPTED 상태인 친구들만 가져옵니다.
     * @param user 현재 로그인 유저
     */
    @Transactional(readOnly = true)
    public void getFriendAll(User user) {
        List<Friendship> friends = friendShipRepository.findAllByReceiverIdAndStatus(
            user.getId(), FriendShipStatus.ACCEPTED);

        // todo 유저 정보 dto 반환
    }

    /**
     * 친구삭제
     * @param user 현재 로그인 유저
     * @param removeUserId 삭제할 친구(유저) 아이디
     */
    public void removeFriendship(User user, Long removeUserId) {
        User removeUser = getUserOrElseThrow(removeUserId);
        friendShipRepository.deleteByRequesterIdAndReceiverId(user.getId(), removeUser.getId());
        friendShipRepository.deleteByRequesterIdAndReceiverId(removeUser.getId(), user.getId());
    }

    private User getUserOrElseThrow(Long id){
        return userRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아이디입니다."));
    }

    /**
     * 친구들이 작성한 글 목록 가져오기
     * @param user
     */
    public void getFriendPosts(User user) {
    }
}
