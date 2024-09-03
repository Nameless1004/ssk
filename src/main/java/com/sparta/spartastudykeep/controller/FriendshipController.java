package com.sparta.spartastudykeep.controller;

import com.sparta.spartastudykeep.service.FriendshipService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FriendshipController {

    private final FriendshipService friendshipService;

    @PostMapping("/api/users/{from}/friends/{to}")
    public void addFriend(@PathVariable("from") Long from, @PathVariable("to") Long to){
        friendshipService.requestFriendship(from, to);
    }

    @DeleteMapping("/api/users/{from}/friends/{to}")
    public void rejectFriendship(@PathVariable("from") Long from, @PathVariable("to") Long to){
        friendshipService.rejectFriendshipRequest(from, to);
    }

    @GetMapping("/api/users/{from}/friends/{to}")
    public void acceptFriendship(@PathVariable("from") Long from, @PathVariable("to") Long to){
        friendshipService.acceptFriendShip(from, to);
    }

    @GetMapping("/api/users/{id}/friends")
    public void getFriends(@PathVariable("id") Long id){
        friendshipService.getFriendAll(id);
    }

}
