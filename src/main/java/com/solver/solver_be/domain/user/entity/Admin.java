package com.solver.solver_be.domain.user.entity;

import com.solver.solver_be.domain.company.entity.Company;
import com.solver.solver_be.domain.user.dto.AdminSignupRequestDto;
import com.solver.solver_be.domain.user.dto.GuestSignupRequestDto;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Admin{
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

    @ManyToOne
    @JoinColumn(name = "COMPANY_ID")
    private Company company;

    public static Admin of(String userId, String password, String phoneNum, UserRoleEnum role, Company company) {
        return Admin.builder()
                .userId(userId)
                .password(password)
                .phoneNum(phoneNum)
                .role(role)
                .company(company)
                .build();
    }

    public static Admin of(AdminSignupRequestDto signupRequestDto, String password, UserRoleEnum role, Company company) {
        return Admin.builder()
                .userId(signupRequestDto.getUserId())
                .password(password)
                .phoneNum(signupRequestDto.getPhoneNum())
                .name(signupRequestDto.getName())
                .role(role)
                .company(company)
                .build();
    }
    public static Admin of(AdminSignupRequestDto signupRequestDto, String password, UserRoleEnum role) {
        return Admin.builder()
                .userId(signupRequestDto.getUserId())
                .password(password)
                .phoneNum(signupRequestDto.getPhoneNum())
                .name(signupRequestDto.getName())
                .role(role)
                .build();
    }

    public static Admin of(GuestSignupRequestDto signupRequestDto, String userId, String password, UserRoleEnum role) {
        return Admin.builder()
                .userId(userId)
                .password(password)
                .phoneNum(signupRequestDto.getPhoneNum())
                .name(signupRequestDto.getName())
                .role(role)
                .build();
    }
}

