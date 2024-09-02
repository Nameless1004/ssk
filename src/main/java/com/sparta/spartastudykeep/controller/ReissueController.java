package com.sparta.spartastudykeep.controller;

import com.sparta.spartastudykeep.common.enums.TokenType;
import com.sparta.spartastudykeep.common.enums.UserRole;
import com.sparta.spartastudykeep.entity.RefreshEntity;
import com.sparta.spartastudykeep.repository.RefreshRepository;
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

    private final JwtUtil jwtUtil;
    private final RefreshRepository refreshRepository;

    // 스케쥴 작업으로 DB에 기한이 지난 토큰들 정리해 줄 필요가 있다.

    @PostMapping("/api/reissue")
    public ResponseEntity<?> reissue(HttpServletRequest request, HttpServletResponse response) {

        String refreshToken = null;
        for (Cookie cookie : request.getCookies()) {
            if (cookie.getName().equals(TokenType.REFRESH.name())) {
                refreshToken = cookie.getValue();
                break;
            }
        }

        if(refreshToken == null) {
            return ResponseEntity.badRequest().body("refresh token null");
        }

        // decode 해준 후 prefix 제거
        refreshToken = jwtUtil.substringToken(jwtUtil.getDecodeToken(refreshToken));

        // Refresh token 만료 검증
        try{
            jwtUtil.isExpired(refreshToken);
        } catch (ExpiredJwtException e) {
            return ResponseEntity.badRequest().body("refresh token expired");
        }

        // 토큰이 refresh인지 체크
        String categry = jwtUtil.getCategory(refreshToken);
        if (!categry.equals(TokenType.REFRESH.name())) {
            return ResponseEntity.badRequest().body("refresh token invalid");
        }

        // DB에 저장되어 있는지 확인
        boolean isExist = refreshRepository.existsByRefresh(refreshToken);
        if (!isExist) {
            return ResponseEntity.badRequest().body("refresh token invalid");
        }

        String userEmail = jwtUtil.getUserEmail(refreshToken);
        String role = jwtUtil.getRole(refreshToken);

        // 새 토큰 발급
        String newAccessToken = jwtUtil.createToken(TokenType.ACCESS, userEmail, UserRole.valueOf(role));
        String newRefreshToken = jwtUtil.createToken(TokenType.REFRESH, userEmail, UserRole.valueOf(role));

        // Refresh 토큰 저장소에 기존의 Refresh 토큰 삭제 후 새 Refresh 토큰 저장
        refreshRepository.deleteByRefresh(refreshToken);
        // 새로 발급한 토큰에 prefix를 제거 해준 후 저장
        addRefreshEntity(userEmail, jwtUtil.substringToken(newRefreshToken));

        response.setHeader(JwtUtil.AUTHORIZATION_HEADER, newAccessToken);
        response.addCookie(jwtUtil.createCookie(TokenType.REFRESH, newRefreshToken));
        return ResponseEntity.ok().build();
    }

    /**
     * 리프레쉬 토큰 테이블에 추가
     * @param userEmail
     * @param refreshToken
     */
    private void addRefreshEntity(String userEmail, String refreshToken) {
        Long refreshTokenTime = jwtUtil.getRefreshTokenTime();
        Date date = new Date(System.currentTimeMillis() + refreshTokenTime);

        RefreshEntity refreshEntity = RefreshEntity.builder()
            .userEmail(userEmail)
            .refresh(refreshToken)
            .expiration(date.toString())
            .build();

        refreshRepository.save(refreshEntity);
    }
}