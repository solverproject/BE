package com.solver.solver_be.domain.user.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.solver.solver_be.domain.user.dto.LoginRequestDto;
import com.solver.solver_be.domain.user.dto.SignupRequestDto;
import com.solver.solver_be.domain.user.service.SocialLoginService;
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
@RequestMapping("/")
public class UserController {

    private final UserService userService;
    private final SocialLoginService socialLoginService;
    private final String naver = "naver";
    private final String google = "google";
    private final String kakao = "kakao";

    @PostMapping("signup")
    public ResponseEntity<GlobalResponseDto> signup(@Valid @RequestBody SignupRequestDto signupRequestDto) {
        return userService.signup(signupRequestDto);
    }

    @PostMapping("login")
    public ResponseEntity<GlobalResponseDto> login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
        return userService.login(loginRequestDto, response);
    }

    @GetMapping("kakao/callback")
    public ResponseEntity<GlobalResponseDto> kakaoLogin(@RequestParam String code, HttpServletResponse response) throws JsonProcessingException {
        return socialLoginService.socialLogin(kakao, code, null, response);
    }

    @GetMapping("naver/callback")
    public ResponseEntity<GlobalResponseDto> naverLogin(@RequestParam String code, @RequestParam String state, HttpServletResponse response) throws JsonProcessingException {
        return socialLoginService.socialLogin(naver, code, state, response);
    }

    @GetMapping("google/callback")
    public ResponseEntity<GlobalResponseDto> googleLogin(@RequestParam String code, HttpServletResponse response) throws JsonProcessingException {
        return socialLoginService.socialLogin(google, code, null, response);
    }
}