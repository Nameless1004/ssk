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

    // 현재 로그인 중인 유저의 정보를 상세 조회 - 토큰 유효한지 빠르게 검증하기 위해 추가함
    @GetMapping("/me")
    public ResponseEntity<UserResponseDto> getMyProfile(
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.status(HttpStatus.OK)
            .body(userService.getMyProfile(userDetails));
    }

    // id를 파라미터로 입력 받아 유저의 정보를 상세 조회
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getProfileById(
        @AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK)
            .body(userService.getProfileById(userDetails, id));
    }

    // 유저 목록 조회
    @GetMapping
    public ResponseEntity<List<UsersResponseDto>> getAllUsers() {
        return ResponseEntity.status(HttpStatus.OK)
            .body(userService.getAllUsers());
    }

    // 유저 description 수정
    @PutMapping
    public ResponseEntity<UserResponseDto> updateUser(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @RequestBody UserRequestDto requestDto) {
        return ResponseEntity.status(HttpStatus.OK)
            .body(userService.updateUser(userDetails, requestDto));
    }

    // 유저 password 변경
    @PutMapping("/password")
    public ResponseEntity<UserResponseDto> updatePassword(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @Valid @RequestBody PasswordRequestDto requestDto) {
        return ResponseEntity.status(HttpStatus.OK)
            .body(userService.updatePassword(userDetails, requestDto));
    }

    // 유저 soft delete -> enable을 false로 변경하여 해당 유저의 상태를 비활성화
    @DeleteMapping
    public Long deleteUser(@AuthenticationPrincipal UserDetailsImpl userDetails,
        @RequestBody DeleteUserRequestDto requestDto) {
        return userService.deleteUser(userDetails, requestDto);
    }
}
