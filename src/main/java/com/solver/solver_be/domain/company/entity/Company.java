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

    @Column(unique = true)
    private String businessCode;

    @Column(nullable = false, unique = true)
    private String companyName;

    @Column
    private Integer x;

    @Column
    private Integer y;

    public static Company of(String businessNum, String businessCode, String companyName, Integer x, Integer y) {
        return Company.builder()
                .businessNum(businessNum)
                .businessCode(businessCode)
                .companyName(companyName)
                .x(x)
                .y(y)
                .build();
    }

    public static Company of(CompanyRequestDto companyRequestDto) {
        return Company.builder()
                .businessNum(companyRequestDto.getBusinessNum())
                .businessCode(companyRequestDto.getBusinessCode())
                .companyName(companyRequestDto.getCompanyName())
                .x(companyRequestDto.getX())
                .y(companyRequestDto.getY())
                .build();
    }

    public static Company of(AdminSignupRequestDto adminSignupRequestDto, Company company) {
        return Company.builder()
                .businessNum(adminSignupRequestDto.getBusinessNum())
                .businessCode(company.getBusinessCode())
                .companyName(adminSignupRequestDto.getCompanyName())
                .x(company.getX())
                .y(company.getY())
                .build();
    }

    public void update(CompanyRequestDto companyRequestDto) {
        this.businessNum = companyRequestDto.getBusinessNum();
        this.businessCode = companyRequestDto.getBusinessCode();
        this.companyName = companyRequestDto.getCompanyName();
        this.x = companyRequestDto.getX();
        this.y = companyRequestDto.getY();
    }
}
