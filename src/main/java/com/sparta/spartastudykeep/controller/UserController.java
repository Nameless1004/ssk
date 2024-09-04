package com.sparta.spartastudykeep.controller;


import com.sparta.spartastudykeep.dto.*;
import com.sparta.spartastudykeep.security.UserDetailsImpl;
import com.sparta.spartastudykeep.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

//    @PostMapping
//    public ResponseEntity<UserResponseDto> createUser(@RequestBody UserRequestDto requestDto) {
//        UserResponseDto userResponseDto = userService.createUser(requestDto);
//        return ResponseEntity.status(HttpStatus.CREATED).body(userResponseDto);
//    }

    @GetMapping("/me")
    public ResponseEntity<UserResponseDto> getUser(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUser(userDetails));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserById(userDetails, id));
    }

    @GetMapping
    public ResponseEntity<List<UsersResponseDto>> getAllUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAllUsers());
    }

    @PutMapping
    public ResponseEntity<UserResponseDto> updateUser(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody UserRequestDto requestDto) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.updateUser(userDetails, requestDto));
    }

    @PutMapping("/password")
    public ResponseEntity<UserResponseDto> updatePassword(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody PasswordRequestDto requestDto) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.updatePassword(userDetails, requestDto));
    }

    @DeleteMapping
    public Long deleteUser(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody DeleteUserRequestDto requestDto) {
        return userService.deleteUser(userDetails, requestDto);
    }
}
