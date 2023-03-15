package com.solver.solver_be.domain.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SocialLoginRequestDto {
    private String id;
    private String nickname;
    private String userEmail;

    @Builder
    public SocialLoginRequestDto(String id, String nickname, String userEmail) {
        this.id = id;
        this.nickname = nickname;
        this.userEmail = userEmail;
    }
}
