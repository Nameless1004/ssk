package com.sparta.spartastudykeep.controller;

import com.sparta.spartastudykeep.entity.User;
import com.sparta.spartastudykeep.security.UserDetailsImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TokenExampleController {

    @GetMapping("/api/test")
    public ResponseEntity<Void> test(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        System.out.println("user.getUsername() = " + user.getUsername());
        System.out.println("user.getEmail() = " + user.getEmail());
        return ResponseEntity.ok().build();
    }
}
