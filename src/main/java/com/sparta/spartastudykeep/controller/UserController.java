package com.sparta.spartastudykeep.controller;


import com.sparta.spartastudykeep.dto.DeleteUserRequestDto;
import com.sparta.spartastudykeep.dto.PasswordRequestDto;
import com.sparta.spartastudykeep.dto.UserRequestDto;
import com.sparta.spartastudykeep.dto.UserResponseDto;
import com.sparta.spartastudykeep.dto.UsersResponseDto;
import com.sparta.spartastudykeep.security.UserDetailsImpl;
import com.sparta.spartastudykeep.service.UserService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<UserResponseDto> getMyProfile(
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.status(HttpStatus.OK)
            .body(userService.getMyProfile(userDetails));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getProfileById(
        @AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK)
            .body(userService.getProfileById(userDetails, id));
    }

    @GetMapping
    public ResponseEntity<List<UsersResponseDto>> getAllUsers() {
        return ResponseEntity.status(HttpStatus.OK)
            .body(userService.getAllUsers());
    }

    @PutMapping
    public ResponseEntity<UserResponseDto> updateUser(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @RequestBody UserRequestDto requestDto) {
        return ResponseEntity.status(HttpStatus.OK)
            .body(userService.updateUser(userDetails, requestDto));
    }

    @PutMapping("/password")
    public ResponseEntity<UserResponseDto> updatePassword(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @Valid @RequestBody PasswordRequestDto requestDto) {
        return ResponseEntity.status(HttpStatus.OK)
            .body(userService.updatePassword(userDetails, requestDto));
    }

    @DeleteMapping
    public Long deleteUser(@AuthenticationPrincipal UserDetailsImpl userDetails,
        @RequestBody DeleteUserRequestDto requestDto) {
        return userService.deleteUser(userDetails, requestDto);
    }
}
