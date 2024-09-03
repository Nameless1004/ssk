package com.sparta.spartastudykeep.controller;

import com.sparta.spartastudykeep.dto.SignupRequestDto;
import com.sparta.spartastudykeep.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SignupController {

    private final UserService userService;

    @PostMapping("/api/signup")
    public ResponseEntity<Void> signup(@Valid @RequestBody SignupRequestDto requestDto, HttpServletResponse res){
        userService.createUser(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}