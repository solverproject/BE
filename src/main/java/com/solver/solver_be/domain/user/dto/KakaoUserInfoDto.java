package com.solver.solver_be.domain.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class KakaoUserInfoDto {

    private Long id;
    private String username;
    private String nickname;

    public KakaoUserInfoDto(Long id, String nickname, String username) {
        this.id = id;
        this.nickname = nickname;
        this.username = username;
    }
}