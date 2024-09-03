package com.sparta.spartastudykeep.controller;

import com.sparta.spartastudykeep.common.enums.UserRole;
import com.sparta.spartastudykeep.dto.LoginRequestDto;
import com.sparta.spartastudykeep.dto.SignupRequestDto;
import com.sparta.spartastudykeep.entity.User;
import com.sparta.spartastudykeep.repository.UserRepository;
import com.sparta.spartastudykeep.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SignupController {

    private final UserService userService;

    @PostMapping("/api/signup")
    public ResponseEntity<Void> signup(@RequestBody SignupRequestDto requestDto, HttpServletResponse res){
        userService.createUser(requestDto);
        return ResponseEntity.ok().build();
    }
}