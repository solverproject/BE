package com.solver.solver_be.domain.user.dto;

import lombok.Getter;

@Getter
public class LoginRequestDto {
    private String userEmail;
    private String password;
}