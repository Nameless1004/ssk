package com.sparta.spartastudykeep.service;

import com.sparta.spartastudykeep.common.enums.FriendShipStatus;
import com.sparta.spartastudykeep.dto.FriendRequestDto;
import com.sparta.spartastudykeep.dto.FriendResponseDto;
import com.sparta.spartastudykeep.dto.FriendshipReceiveDto;
import com.sparta.spartastudykeep.entity.Friendship;
import com.sparta.spartastudykeep.entity.User;
import com.sparta.spartastudykeep.repository.FriendShipRepository;
import com.sparta.spartastudykeep.repository.UserRepository;
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
     * @param user 현재 로그인 유저
     * @param requesterId 친구요청한 유저의 아이디
     */
    public void rejectFriendshipRequest(User user, Long requesterId) {
        User requester = getUserOrElseThrow(requesterId);
        boolean isExists = friendShipRepository.existsByRequesterAndReceiverAndStatus(requester, user,
            FriendShipStatus.ACCEPTED);

        if(isExists){
            throw new IllegalArgumentException("이미 수락한 요청입니다.");
        }

        friendShipRepository.deleteByRequesterAndReceiver(requester, user);
    }

    /**
     * 친구요청 수락
     * @param user 현재 로그인 유저
     * @param requesterId 친구요청한 유저의 아이디
     */
    public void acceptFriendShip(User user, Long requesterId) {
        User requester = getUserOrElseThrow(requesterId);

        Friendship request = friendShipRepository.findByRequesterAndReceiver(requester, user)
            .orElseThrow(() -> new IllegalArgumentException("수락할 요청이 없습니다."));

        if(request.getStatus() == FriendShipStatus.ACCEPTED){
            throw new IllegalArgumentException("이미 수락한 요청입니다.");
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
     * @param user 현재 로그인 유저
     */
    @Transactional(readOnly = true)
    public List<FriendResponseDto> getFriendAll(User user) {
        List<Friendship> friends = friendShipRepository.findAllByReceiverAndStatus(
            user, FriendShipStatus.ACCEPTED);

        return friends.stream().map(x-> new FriendResponseDto(x.getRequester())).toList();
    }

    /**
     * 친구삭제 or 요청 거절
     * @param user 현재 로그인 유저
     * @param removeUserId 삭제(거절)할 친구(유저) 아이디
     */
    public void removeFriendship(User user, Long removeUserId) {
        User removeUser = getUserOrElseThrow(removeUserId);
        friendShipRepository.deleteByRequesterAndReceiver(removeUser, user);
        friendShipRepository.deleteByRequesterAndReceiver(user,removeUser);
    }

    /**
     * 친구요청 목록
     * @param user
     */
    public List<FriendshipReceiveDto> getRecieveList(User user) {
        List<Friendship> findAll = friendShipRepository.findAllByReceiverAndStatus(
            user, FriendShipStatus.WAITING);

        return findAll.stream().map(x->
            FriendshipReceiveDto.builder()
                .friendshipId(x.getId())
                .requesterId(x.getRequester().getId())
                .receiverId(user.getId())
                .build()).toList();
    }

    /**
     * 친구 요청, 친구 요청받은 것, 친구 관계 삭제
     * @param user 현재 로그인한 유저
     */
    public void removeAllFriendship(User user) {
        friendShipRepository.deleteAllByRequesterOrReceiver(user, user);
    }

    private User getUserOrElseThrow(Long id){
        return userRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아이디입니다."));
    }

}
