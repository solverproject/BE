package com.solver.solver_be.domain.user.dto;

import com.solver.solver_be.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginResponseDto {

    private String nickname;

    @Builder
    private LoginResponseDto(String nickname) {
        this.nickname = nickname;
    }

    public static LoginResponseDto of(User user) {
        return LoginResponseDto.builder()
                .build();
    }
}
