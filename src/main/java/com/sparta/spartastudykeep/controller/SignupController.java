package com.sparta.spartastudykeep.controller;

import com.sparta.spartastudykeep.common.enums.UserRole;
import com.sparta.spartastudykeep.dto.LoginRequestDto;
import com.sparta.spartastudykeep.dto.SignupRequestDto;
import com.sparta.spartastudykeep.entity.User;
import com.sparta.spartastudykeep.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SignupController {

    // private final UserService userService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @PostMapping("/api/signup")
    public ResponseEntity<Void> signup(@RequestBody SignupRequestDto requestDto, HttpServletResponse res){
        // todo 추후에 서비스에 유저 등록하는 메서드 만들어야 함.
        // userService.save(requestDto);
        User asdf = User.builder()
            .enabled(true)
            .username("정재호")
            .email("jjho1029@gmail.com")
            .role(UserRole.USER)
            .password(passwordEncoder.encode("123"))
            .description("asdf")
            .build();
        userRepository.save(asdf);
        return ResponseEntity.ok().build();
    }


}