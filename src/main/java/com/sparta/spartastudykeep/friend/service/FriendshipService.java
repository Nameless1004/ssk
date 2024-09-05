package com.sparta.spartastudykeep.friend.service;

import com.sparta.spartastudykeep.common.enums.FriendShipStatus;
import com.sparta.spartastudykeep.common.exception.AlreadyAcceptedException;
import com.sparta.spartastudykeep.common.exception.InvalidIdException;
import com.sparta.spartastudykeep.common.exception.NoSuchResourceException;
import com.sparta.spartastudykeep.friend.dto.FriendRequestDto;
import com.sparta.spartastudykeep.friend.dto.FriendResponseDto;
import com.sparta.spartastudykeep.friend.dto.FriendshipReceiveDto;
import com.sparta.spartastudykeep.friend.entity.Friendship;
import com.sparta.spartastudykeep.user.entity.User;
import com.sparta.spartastudykeep.friend.repository.FriendShipRepository;
import com.sparta.spartastudykeep.user.repository.UserRepository;
import java.util.List;
import java.util.Set;
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
     *
     * @param user       현재 로그인 유저
     * @param requestDto 친구요청 받을 유저의 아이디
     */
    public void requestFriendship(User user, FriendRequestDto requestDto) {
        User receiver = getUserOrElseThrow(requestDto.getReceiverId());
        Friendship friendship = new Friendship(user, receiver);
        friendShipRepository.save(friendship);
    }

    /**
     * 친구요청 거절
     *
     * @param user        현재 로그인 유저
     * @param requesterId 친구요청한 유저의 아이디
     */
    public void rejectFriendshipRequest(User user, Long requesterId) {
        User requester = getUserOrElseThrow(requesterId);

        if(!friendShipRepository.existsByRequesterAndReceiverAndStatus(requester, user, FriendShipStatus.WAITING)){
            throw new NoSuchResourceException("거절할 요청이 없습니다.");
        }

        if (friendShipRepository.existsByRequesterAndReceiverAndStatus(requester, user, FriendShipStatus.ACCEPTED)) {
            throw new AlreadyAcceptedException();
        }

        friendShipRepository.deleteByRequesterAndReceiver(requester, user);
    }

    /**
     * 친구요청 수락
     *
     * @param user        현재 로그인 유저
     * @param requesterId 친구요청한 유저의 아이디
     */
    public void acceptFriendShip(User user, Long requesterId) {
        User requester = getUserOrElseThrow(requesterId);

        Friendship request = friendShipRepository.findByRequesterAndReceiver(requester, user)
            .orElseThrow(() -> new NoSuchResourceException("수락할 요청이 없습니다."));

        if (request.getStatus() == FriendShipStatus.ACCEPTED) {
            throw new AlreadyAcceptedException();
        }

        Friendship receive = new Friendship(user, requester);

        // 서로 요청 상태 accepted로 변경
        request.accept();
        receive.accept();

        // 서로 연결
        friendShipRepository.save(receive);
    }

    /**
     * 친구목록 가져오기, ACCEPTED 상태인 친구들만 가져옵니다.
     *
     * @param user 현재 로그인 유저
     */
    @Transactional(readOnly = true)
    public List<FriendResponseDto> getFriendAll(User user) {
        Set<Friendship> friends = friendShipRepository.findAllByReceiverAndStatus(
            user, FriendShipStatus.ACCEPTED);

        return friends.stream()
            .map(x -> new FriendResponseDto(x.getRequester()))
            .toList();
    }

    /**
     * 친구삭제
     *
     * @param user         현재 로그인 유저
     * @param removeUserId 삭제할 친구(유저) 아이디
     */
    public void removeFriendship(User user, Long removeUserId) {
        User removeUser = getUserOrElseThrow(removeUserId);
        if(!friendShipRepository.existsByRequesterAndReceiverAndStatus(removeUser, user, FriendShipStatus.ACCEPTED)){
            throw new AlreadyAcceptedException("이미 삭제된 친구입니다.");
        }

        friendShipRepository.deleteByRequesterAndReceiver(removeUser, user);
        friendShipRepository.deleteByRequesterAndReceiver(user, removeUser);
    }

    /**
     * 친구요청 목록
     *
     * @param user
     */
    public List<FriendshipReceiveDto> getRecieveList(User user) {
        Set<Friendship> findAll = friendShipRepository.findAllByReceiverAndStatus(
            user, FriendShipStatus.WAITING);

        return findAll.stream()
            .map(x ->
                FriendshipReceiveDto.builder()
                    .friendshipId(x.getId())
                    .requesterId(x.getRequester()
                        .getId())
                    .receiverId(user.getId())
                    .build())
            .toList();
    }

    /**
     * 친구 요청, 친구 요청받은 것, 친구 관계 삭제
     *
     * @param user 현재 로그인한 유저
     */
    public void removeAllFriendship(User user) {
        friendShipRepository.deleteAllByRequesterOrReceiver(user, user);
    }

    private User getUserOrElseThrow(Long id) {
        return userRepository.findById(id)
            .orElseThrow(InvalidIdException::new);
    }

}
