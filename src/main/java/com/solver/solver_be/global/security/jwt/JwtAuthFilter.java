package com.solver.solver_be.global.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.solver.solver_be.global.security.refreshtoken.RefreshToken;
import com.solver.solver_be.global.security.refreshtoken.RefreshTokenRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String accessToken = jwtUtil.resolveToken(request, "Access");
        String refreshToken = jwtUtil.resolveToken(request, "Refresh");

        if (accessToken != null && refreshToken != null) {
            if (!jwtUtil.validateToken(accessToken)) { // access토큰이 유효하지 않지만
                if (!jwtUtil.refreshTokenValidation(refreshToken)) { // refresh 토큰이 유효하면
                    jwtExceptionHandler(response, "Token Error", HttpStatus.UNAUTHORIZED);
                    return;
                } else {
                    // refresh토큰과 같은 토큰을 db에서 찾아온후
                    Optional<RefreshToken> foundToken = refreshTokenRepository.findByRefreshToken("Bearer " + refreshToken);
                    if (foundToken.isEmpty()) {
                        throw new IllegalArgumentException("다시 로그인 하세요.");
                    }
                    //access토큰을 재발급
                    response.addHeader(JwtUtil.ACCESS_TOKEN, jwtUtil.createToken(foundToken.get().getUserEmail(), "Access"));
                }
            }
            Claims info = jwtUtil.getUserInfoFromToken(refreshToken);
            setAuthentication(info.getSubject());
        }
        filterChain.doFilter(request, response);
    }

    private void setAuthentication(String userEmail) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication = jwtUtil.createAuthentication(userEmail);// 인증 객체 생성
        context.setAuthentication(authentication);

        SecurityContextHolder.setContext(context);
    }

    public void jwtExceptionHandler(HttpServletResponse response, String msg, HttpStatus status) {
        response.setStatus(status.value());
        response.setContentType("application/json");
        try {
            String json = new ObjectMapper().writeValueAsString(ResponseEntity.status(HttpStatus.UNAUTHORIZED));
            response.getWriter().write(json);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}