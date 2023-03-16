package com.solver.solver_be.global.security.jwt;

import com.solver.solver_be.global.response.ResponseCode;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String accessToken = jwtUtil.resolveToken(request, "Access");
        String refreshToken = jwtUtil.resolveToken(request, "Refresh");


        // 1. 토큰이 없는 상황
        if (accessToken == null) {
            request.setAttribute("exception", ResponseCode.TOKEN_NOT_FOUND);
            filterChain.doFilter(request, response);
            return;
        }
        // 2. 토큰이 유효하지 않은 상황
        if (!jwtUtil.validateToken(accessToken)) {
            if (refreshToken != null) {
                boolean validateRefreshToken = jwtUtil.validateToken(refreshToken);
                if (validateRefreshToken) {
                    String userEmail = jwtUtil.getUserEmail(refreshToken);
                    String newAccessToken = jwtUtil.createToken(userEmail, "Access");
                    response.addHeader(JwtUtil.ACCESS_TOKEN, newAccessToken);
                    this.setAuthentication(userEmail);
                    filterChain.doFilter(request, response);
                    return;
                } else {
                    request.setAttribute("exception", ResponseCode.NOT_VALID_REFRESH_TOKEN);
                    filterChain.doFilter(request, response);
                    return;
                }
            } else {
                request.setAttribute("exception", ResponseCode.NOT_VALID_TOKEN);
                filterChain.doFilter(request, response);
                return;
            }
        }

        Claims info = jwtUtil.getUserInfoFromToken(accessToken);
        try {
            setAuthentication(info.getSubject());
        } catch (UsernameNotFoundException e) {
            request.setAttribute("exception", ResponseCode.USER_NOT_FOUND);
        }
        filterChain.doFilter(request, response);
    }

    private void setAuthentication(String userEmail) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication = jwtUtil.createAuthentication(userEmail);// 인증 객체 생성
        context.setAuthentication(authentication);

        SecurityContextHolder.setContext(context);
    }

}