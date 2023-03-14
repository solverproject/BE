package com.solver.solver_be.domain.user.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.solver.solver_be.domain.user.dto.LoginRequestDto;
import com.solver.solver_be.domain.user.dto.SignupRequestDto;
import com.solver.solver_be.domain.user.service.KakaoService;
import com.solver.solver_be.domain.user.service.UserService;
import com.solver.solver_be.global.response.GlobalResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users/")
public class UserController {

    private final UserService userService;
    private final KakaoService kakaoService;

    //회원 가입
    @PostMapping("signup")
    public ResponseEntity<GlobalResponseDto> signup(@Valid @RequestBody SignupRequestDto signupRequestDto) {
        return userService.signup(signupRequestDto);
    }

    // 로그인
    @PostMapping("login")
    public ResponseEntity<GlobalResponseDto> login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
        return userService.login(loginRequestDto, response);
    }

    @GetMapping("/kakao/callback")
    public ResponseEntity<GlobalResponseDto> kakaoLogin(@RequestParam String code, HttpServletResponse response) throws JsonProcessingException {
        return kakaoService.kakaoLogin(code, response);
    }
}