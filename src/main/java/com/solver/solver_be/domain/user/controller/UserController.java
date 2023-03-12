package com.solver.solver_be.domain.user.controller;

import com.solver.solver_be.domain.user.dto.SignupRequestDto;
import com.solver.solver_be.domain.user.service.UserService;
import com.solver.solver_be.global.response.GlobalResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    //회원 가입
    @PostMapping("/users/signup")
    public ResponseEntity<GlobalResponseDto> signup(@Valid @RequestBody SignupRequestDto signupRequestDto){
        return userService.signup(signupRequestDto);
    }
}
