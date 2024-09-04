package com.sparta.spartastudykeep.controller;

import com.sparta.spartastudykeep.dto.FriendRequestDto;
import com.sparta.spartastudykeep.dto.FriendResponseDto;
import com.sparta.spartastudykeep.dto.FriendshipReceiveDto;
import com.sparta.spartastudykeep.security.UserDetailsImpl;
import com.sparta.spartastudykeep.service.FriendshipService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FriendshipController {

    private final FriendshipService friendshipService;


    /**
     * 다른 유저에게 친구요청
     *
     * @param userDetails 현재 로그인 유저
     * @param requestDto  receiver 아이디 필요
     * @return
     */
    @PostMapping("/api/friend-requests")
    public ResponseEntity<Void> addFriend(@AuthenticationPrincipal UserDetailsImpl userDetails,
        @RequestBody FriendRequestDto requestDto) {
        friendshipService.requestFriendship(userDetails.getUser(), requestDto);
        return ResponseEntity.ok()
            .build();
    }

    /**
     * 친구요청 수락
     *
     * @param userDetails 현재 로그인 유저
     * @param requesterId 요청한 유저 아이디
     * @return
     */
    @PutMapping("/api/friend-requests/{requesterId}/accept")
    public ResponseEntity<Void> acceptFriendship(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @PathVariable("requesterId") Long requesterId) {
        friendshipService.acceptFriendShip(userDetails.getUser(), requesterId);
        return ResponseEntity.ok()
            .build();
    }

    /**
     * 친구요청 거절
     *
     * @param userDetails 현재 로그인 유저
     * @param requesterId 요청한 유저 아이디
     */
    @DeleteMapping("/api/friend-requests/{requesterId}/reject")
    public ResponseEntity<Void> rejectFriendRequest(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @PathVariable("requesterId") Long requesterId) {
        friendshipService.rejectFriendshipRequest(userDetails.getUser(), requesterId);
        return ResponseEntity.ok()
            .build();
    }

    /**
     * 친구요청 목록
     *
     * @param userDetails 현재 로그인 유저
     */
    @GetMapping("/api/friend-requests")
    public ResponseEntity<List<FriendshipReceiveDto>> getRecieveList(
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(friendshipService.getRecieveList(userDetails.getUser()));
    }

    /**
     * 친구목록 가져오기
     *
     * @param userDetails 현재 로그인 유저
     */
    @GetMapping("/api/friends")
    public ResponseEntity<List<FriendResponseDto>> getFriends(
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(friendshipService.getFriendAll(userDetails.getUser()));
    }

    /**
     * 친구 삭제
     *
     * @param userDetails 현재 로그인 유저
     * @param id          삭제할 친구(유저) 아이디
     */
    @DeleteMapping("/api/friends/{id}")
    public ResponseEntity<Void> removeFriend(@AuthenticationPrincipal UserDetailsImpl userDetails,
        @PathVariable("id") Long id) {
        friendshipService.removeFriendship(userDetails.getUser(), id);
        return ResponseEntity.ok()
            .build();
    }

    /**
     * 모든 친구 요청 및 친구 삭제
     *
     * @param userDetails
     */
    @DeleteMapping("/api/friends")
    public ResponseEntity<Void> removeAllFriend(
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        friendshipService.removeAllFriendship(userDetails.getUser());
        return ResponseEntity.ok()
            .build();
    }


}
