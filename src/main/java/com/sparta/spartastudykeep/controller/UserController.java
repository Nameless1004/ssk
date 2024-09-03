package com.sparta.spartastudykeep.controller;


import com.sparta.spartastudykeep.dto.PasswordRequestDto;
import com.sparta.spartastudykeep.dto.UserRequestDto;
import com.sparta.spartastudykeep.dto.UserResponseDto;
import com.sparta.spartastudykeep.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@RequestBody UserRequestDto requestDto) {
        UserResponseDto userResponseDto = userService.createUser(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponseDto);
    }

    @GetMapping("/{id}")
    public UserResponseDto getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @GetMapping
    public List<UserResponseDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @PutMapping("/{id}")
    public UserResponseDto updateUser(@PathVariable Long id, @RequestBody UserRequestDto requestDto) {
        return userService.updateUser(id, requestDto);
    }

    @PutMapping("/password/{id}")
    public UserResponseDto updatePassword(@PathVariable Long id, @RequestBody PasswordRequestDto requestDto) {
        return userService.updatePassword(id, requestDto);
    }

    @DeleteMapping("/{id}")
    public Long deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id);
    }
}
