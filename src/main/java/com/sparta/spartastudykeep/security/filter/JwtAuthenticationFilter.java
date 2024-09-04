package com.sparta.spartastudykeep.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.spartastudykeep.common.enums.TokenType;
import com.sparta.spartastudykeep.common.enums.UserRole;
import com.sparta.spartastudykeep.dto.LoginRequestDto;
import com.sparta.spartastudykeep.entity.RefreshEntity;
import com.sparta.spartastudykeep.repository.RefreshRepository;
import com.sparta.spartastudykeep.security.UserDetailsImpl;
import com.sparta.spartastudykeep.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j(topic = "로그인 및 JWT 생성")
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final JwtUtil jwtUtil;
    private final RefreshRepository refreshRepository;

    public JwtAuthenticationFilter(JwtUtil jwtUtil, RefreshRepository refreshRepository) {
        this.jwtUtil = jwtUtil;
        this.refreshRepository = refreshRepository;
        setFilterProcessesUrl("/api/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
        HttpServletResponse response) throws AuthenticationException {
        log.info("로그인 시도");
        LoginRequestDto loginRequestDto;
        String userEmail;
        String password;
        try {
            loginRequestDto = new ObjectMapper().readValue(request.getInputStream(),
                LoginRequestDto.class);
            userEmail = loginRequestDto.getEmail();
            password = loginRequestDto.getPassword();
        } catch (IOException e) {
            userEmail = request.getParameter("email");
            password = obtainPassword(request);
        }

        log.info("useremail: {} password:{}", userEmail, password);

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
            userEmail, password);

        return getAuthenticationManager().authenticate(authToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
        HttpServletResponse response, FilterChain chain, Authentication authResult)
        throws IOException, ServletException {
        log.info("로그인 성공 및 JWT 생성");
        UserDetailsImpl userDetails = (UserDetailsImpl) authResult.getPrincipal();
        String userEmail = userDetails.getUsername();
        UserRole role = userDetails.getUser()
            .getRole();

        String accessToken = jwtUtil.createToken(TokenType.ACCESS, userEmail, role);
        String refreshToken = jwtUtil.createToken(TokenType.REFRESH, userEmail, role);

        // Refresh 레포지토리에 저장
        // 새로 발급한 토큰에 prefix를 제거 해준 후 저장
        addRefreshEntity(userEmail, jwtUtil.substringToken(refreshToken));

        jwtUtil.addTokenToHeader(response, accessToken);
        jwtUtil.addCookie(response, TokenType.REFRESH, refreshToken);
        response.setStatus(HttpStatus.OK.value());
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
        HttpServletResponse response, AuthenticationException failed)
        throws IOException, ServletException {
        log.info(failed.getMessage());
        log.info("로그인 실패");
        response.setStatus(401);
    }

    /**
     * Encode가 안된 토큰을 넣어줘야합니다.
     *
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