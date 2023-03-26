package com.solver.solver_be.domain.user.controller;

import com.solver.solver_be.domain.user.dto.BusinessSignupRequestDto;
import com.solver.solver_be.domain.user.dto.GuestSignupRequestDto;
import com.solver.solver_be.domain.user.dto.LoginRequestDto;
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
public class UserController {

    private final UserService userService;

    @PostMapping("/signup/business")
    public ResponseEntity<GlobalResponseDto> signupBusiness(@Valid @RequestBody BusinessSignupRequestDto signupRequestDto) {
        return userService.signupBusiness(signupRequestDto);
    }

    @PostMapping("/signup/guest")
    public ResponseEntity<GlobalResponseDto> signupGuest(@Valid @RequestBody GuestSignupRequestDto signupRequestDto) {
        return userService.signupGuest(signupRequestDto);
    }

    @PostMapping("/login/business")
    public ResponseEntity<GlobalResponseDto> loginBusiness(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
        return userService.login(loginRequestDto, response);
    }

    @PostMapping("/login/guest")
    public ResponseEntity<GlobalResponseDto> loginGuest(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
        return userService.login(loginRequestDto, response);
    }

}