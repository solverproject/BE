package com.solver.solver_be.domain.user.dto;

import com.solver.solver_be.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginResponseDto {

    private String businessName;

    private String name;

    private String phoneNum;

    @Builder
    private LoginResponseDto(String businessName, String name, String phoneNum) {
        this.businessName =businessName;
        this.name = name;
        this.phoneNum = phoneNum;
    }

    public static LoginResponseDto of(User user) {
        return LoginResponseDto.builder()
                .name(user.getName())
                .businessName(user.getBusinessName())
                .phoneNum(user.getPhoneNum())
                .build();
    }
}
