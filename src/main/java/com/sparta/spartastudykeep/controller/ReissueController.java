package com.sparta.spartastudykeep.controller;

import com.sparta.spartastudykeep.common.enums.TokenType;
import com.sparta.spartastudykeep.common.enums.UserRole;
import com.sparta.spartastudykeep.entity.RefreshEntity;
import com.sparta.spartastudykeep.repository.RefreshRepository;
import com.sparta.spartastudykeep.service.ReissueService;
import com.sparta.spartastudykeep.util.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class ReissueController {

    private final ReissueService reissueService;

    @PostMapping("/api/reissue")
    public ResponseEntity<?> reissue(HttpServletRequest request, HttpServletResponse response) {
        return reissueService.reissue(request, response);
    }

}