package com.solver.solver_be.domain.user.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
public class SignupRequestDto {

    @NotBlank(message = "아이디는 필수입니다.")
    @Pattern(regexp = "^[a-z0-9]{4,10}$", message = "아이디는 4~10자 영문 대 소문자, 숫자를 사용하세요.")
    private String userId;

    @NotBlank(message = "비밀번호는 필수입니다.")
    @Pattern(regexp = "^[a-zA-Z0-9~!@#$%^&*()_+=?,./<>{}\\[\\]\\-]{8,15}$", message = "비밀번호는 8~15자리 영문 대소문자(a~z, A~Z), 숫자(0~9), 특수문자를 사용하세요.")
    private String password;

    @NotBlank(message = "전화번호는 필수입니다.")
    @Pattern(regexp = "^\\\\d{3}-\\\\d{3,4}-\\\\d{4}$", message = "전화번호는 010-XXXX-XXXX 형식입니다.")
    private String phoneNumber;

    private boolean isAdmin;

    private String companyToken = "";

}