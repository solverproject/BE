package com.solver.solver_be.domain.company.entity;

import com.solver.solver_be.domain.company.dto.CompanyRequestDto;
import com.solver.solver_be.domain.user.dto.AdminSignupRequestDto;
import com.solver.solver_be.global.util.TimeStamped;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Company extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String businessNum;

    @Column(nullable = false, unique = true)
    private String companyName;

    @Column
    private Integer x;

    @Column
    private Integer y;

    @Column(unique = true)
    private String companyToken;

    public static Company of(String businessNum, String companyName, Integer x, Integer y, String companyToken) {
        return Company.builder()
                .businessNum(businessNum)
                .companyName(companyName)
                .x(x)
                .y(y)
                .companyToken(companyToken)
                .build();
    }

    public static Company of(CompanyRequestDto companyRequestDto, String companyToken) {
        return Company.builder()
                .businessNum(companyRequestDto.getBusinessNum())
                .companyName(companyRequestDto.getCompanyName())
                .x(companyRequestDto.getX())
                .y(companyRequestDto.getY())
                .companyToken(companyToken)
                .build();
    }

    public static Company of(AdminSignupRequestDto adminSignupRequestDto, Company company) {
        return Company.builder()
                .businessNum(adminSignupRequestDto.getBusinessNum())
                .companyName(adminSignupRequestDto.getCompanyName())
                .x(company.getX())
                .y(company.getY())
                .build();
    }

    public void update(CompanyRequestDto companyRequestDto) {
        this.businessNum = companyRequestDto.getBusinessNum();
        this.companyName = companyRequestDto.getCompanyName();
        this.x = companyRequestDto.getX();
        this.y = companyRequestDto.getY();
    }
}
