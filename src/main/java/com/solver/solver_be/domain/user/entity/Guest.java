package com.solver.solver_be.domain.user.entity;

import com.solver.solver_be.domain.user.dto.GuestSignupRequestDto;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Guest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String userId;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String phoneNum;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    public static Guest of(String userId, String password, String phoneNum, UserRoleEnum role) {
        return Guest.builder()
                .userId(userId)
                .password(password)
                .phoneNum(phoneNum)
                .role(role)
                .build();
    }

    public static Guest of(GuestSignupRequestDto signupRequestDto, String password, UserRoleEnum role) {
        return Guest.builder()
                .userId(signupRequestDto.getUserId())
                .password(password)
                .phoneNum(signupRequestDto.getPhoneNum())
                .name(signupRequestDto.getName())
                .role(role)
                .build();
    }

    public static Guest of(GuestSignupRequestDto signupRequestDto, String userId, String password, UserRoleEnum role) {
        return Guest.builder()
                .userId(userId)
                .password(password)
                .phoneNum(signupRequestDto.getPhoneNum())
                .name(signupRequestDto.getName())
                .role(role)
                .build();
    }

}
