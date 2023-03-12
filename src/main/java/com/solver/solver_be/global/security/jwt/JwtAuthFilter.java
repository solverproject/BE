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

        String token = jwtUtil.resolveToken(request);

        if (token == null) {
            request.setAttribute("exception", ResponseCode.TOKEN_NOT_FOUND);
            filterChain.doFilter(request, response);
            return;
        }

        if (!jwtUtil.validateToken(token)) {
            request.setAttribute("exception", ResponseCode.NOT_VALID_TOKEN);
            filterChain.doFilter(request, response);
            return;
        }

        Claims info = jwtUtil.getUserInfoFromToken(token);

        try {
            setAuthentication(info.getSubject());
        } catch (UsernameNotFoundException e) {
            request.setAttribute("exception", ResponseCode.USER_NOT_FOUND);
        }

        filterChain.doFilter(request, response);
    }

    private void setAuthentication(String userEmail) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication = jwtUtil.createAuthentication(userEmail);
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
    }
}