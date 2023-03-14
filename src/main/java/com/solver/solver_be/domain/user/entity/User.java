package com.solver.solver_be.domain.user.entity;

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
    private String userEmail;

    @Column(nullable = false, unique = true)
    private String nickname;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    private Long kakaoId;

    @Builder
    private User(String userEmail, String password, UserRoleEnum role, String nickname, Long kakaoId) {
        this.userEmail = userEmail;
        this.password = password;
        this.role = role;
        this.nickname = nickname;
        this.kakaoId = kakaoId;
    }

    public static User of(String userEmail, String password, UserRoleEnum role, String nickname) {
        return User.builder()
                .userEmail(userEmail)
                .password(password)
                .role(role)
                .nickname(nickname)
                .build();
    }

    public static User of(String userEmail, String password, UserRoleEnum role, String nickname, Long kakaoId) {
        return User.builder()
                .userEmail(userEmail)
                .password(password)
                .role(role)
                .nickname(nickname)
                .kakaoId(kakaoId)
                .build();
    }

    public User kakaoIdUpdate(Long kakaoId) {
        this.kakaoId = kakaoId;
        return this;
    }
}

