package com.solver.solver_be.domain.user.dto;

import com.solver.solver_be.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginResponseDto {

    private String companyName;

    private String name;

    private String phoneNum;

    @Builder
    private LoginResponseDto(String companyName, String name, String phoneNum) {
        this.companyName = companyName;
        this.name = name;
        this.phoneNum = phoneNum;
    }

    public static LoginResponseDto of(User user) {
        return LoginResponseDto.builder()
                .name(user.getName())
                .companyName(user.getCompanyName())
                .phoneNum(user.getPhoneNum())
                .build();
    }
}
