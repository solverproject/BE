package com.solver.solver_be.domain.user.entity;

import com.solver.solver_be.domain.user.dto.LoginRequestDto;
import com.solver.solver_be.domain.user.dto.LoginResponseDto;
import lombok.*;

import javax.persistence.*;

@Entity(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String userId;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    @Builder
    private User(String userId, String password, String phoneNumber, UserRoleEnum role) {
        this.userId = userId;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }

    public static User of(String userId, String password, String phoneNumber, UserRoleEnum role) {
        return User.builder()
                .userId(userId)
                .password(password)
                .phoneNumber(phoneNumber)
                .role(role)
                .build();
    }

    public static User of(LoginRequestDto loginRequestDto, UserRoleEnum role) {
        return User.builder()
                .userId(loginRequestDto.getUserId())
                .password(loginRequestDto.getPassword())
                .role(role)
                .build();
    }
}

