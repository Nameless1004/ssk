package com.sparta.spartastudykeep.controller;

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
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FriendshipController {

    private final FriendshipService friendshipService;


    /**
     * 다른 유저에게 친구요청
     * @param userDetails 현재 로그인 유저
     * @param id 친구요청 받을 유저의 아이디
     * @return
     */
    @PostMapping("/api/users/friends/{id}")
    public ResponseEntity<Void> addFriend(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable("id") Long id){
        friendshipService.requestFriendship(userDetails.getUser(), id);
        return ResponseEntity.ok().build();
    }

    /**
     * 친구요청 거절
     * @param userDetails 현재 로그인 유저
     * @param id 친구요청 거절할 유저의 아이디
     * @return
     */
    @DeleteMapping("/api/friends/{id}")
    public ResponseEntity<Void> rejectFriendship(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable("id") Long id){
        friendshipService.rejectFriendshipRequest(userDetails.getUser(), id);
        return ResponseEntity.ok().build();
    }

    /**
     * 받은 친구요청 수락
     * @param userDetails 현재 로그인 유저
     * @param id 친구요청 수락할 유저의 아이디
     * @return
     */
    @GetMapping("/api/friends/{id}")
    public ResponseEntity<Void> acceptFriendship(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable("id") Long id){
        friendshipService.acceptFriendShip(userDetails.getUser(), id);
        return ResponseEntity.ok().build();
    }

    /**
     * 친구목록 가져오기
     * @param userDetails 현재 로그인 유저
     */
    @GetMapping("/api/friends")
    public void getFriends(@AuthenticationPrincipal UserDetailsImpl userDetails){
        friendshipService.getFriendAll(userDetails.getUser());
    }

    /**
     * 친구들이 작성한 글목록 가져오기
     * @param userDetails 현재 로그인 유저
     */
    @GetMapping("/api/friends/posts")
    public void getFriendPosts(@AuthenticationPrincipal UserDetailsImpl userDetails){
        
        // 나중에 
        // todo 보드 생기면 그 때 작업 해야함
        // List<?> posts = friendshipService.getFriendPosts(userDetails.getUser());
    }

    /**
     * 친구 삭제
     * @param userDetails 현재 로그인 유저
     * @param id 삭제할 친구 아이디
     */
    @DeleteMapping("/api/friends/{id}")
    public void removeFriend(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable("id") Long id){
        friendshipService.removeFriendship(userDetails.getUser(), id);
    }

}
