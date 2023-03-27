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
    private String userId;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String phoneNum;

    @Column(nullable = false)
    private String name;
    @Column
    private String businessNum;

    @Column
    private String companyName;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    @Builder
    private User(String userId, String password, String phoneNum, String name, String businessNum, String companyName, UserRoleEnum role) {
        this.userId = userId;
        this.password = password;
        this.phoneNum = phoneNum;
        this.name = name;
        this.companyName = companyName;
        this.businessNum = businessNum;
        this.role = role;
    }

    public static User of(String userId, String password, String phoneNum, UserRoleEnum role) {
        return User.builder()
                .userId(userId)
                .password(password)
                .phoneNum(phoneNum)
                .role(role)
                .build();
    }

    public static User of(BusinessSignupRequestDto signupRequestDto, String userId, String password, UserRoleEnum role) {
        return User.builder()
                .userId(userId)
                .password(password)
                .phoneNum(signupRequestDto.getPhoneNum())
                .name(signupRequestDto.getName())
                .companyName(signupRequestDto.getCompanyName())
                .businessNum(signupRequestDto.getBusinessNum())
                .role(role)
                .build();
    }

    public static User of(GuestSignupRequestDto signupRequestDto, String userId, String password, UserRoleEnum role) {
        return User.builder()
                .userId(userId)
                .password(password)
                .phoneNum(signupRequestDto.getPhoneNum())
                .name(signupRequestDto.getName())
                .role(role)
                .build();
    }
}

