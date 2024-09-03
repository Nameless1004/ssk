package com.sparta.spartastudykeep.controller;

import com.sparta.spartastudykeep.security.UserDetailsImpl;
import com.sparta.spartastudykeep.service.FriendshipService;
import lombok.RequiredArgsConstructor;
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


    @PostMapping("/api/users/friends/{id}")
    public void addFriend(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable("to") Long id){
        friendshipService.requestFriendship(userDetails.getUser(), id);
    }

    @DeleteMapping("/api/friends/{id}")
    public void rejectFriendship(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable("id") Long id){
        friendshipService.rejectFriendshipRequest(userDetails.getUser(), id);
    }

    @GetMapping("/api/friends/{id}")
    public void acceptFriendship(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable("id") Long id){
        friendshipService.acceptFriendShip(userDetails.getUser(), id);
    }

    @GetMapping("/api/friends")
    public void getFriends(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable("id") Long id){
        friendshipService.getFriendAll(userDetails.getUser());
    }

    @DeleteMapping("/api/friends/{id}")
    public void removeFriend(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable("id") Long id){
        friendshipService.removeFriendship(userDetails.getUser(), id);
    }

}
