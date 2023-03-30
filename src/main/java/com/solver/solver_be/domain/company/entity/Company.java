package com.solver.solver_be.domain.company.entity;

import com.solver.solver_be.domain.company.dto.CompanyRequestDto;
import com.solver.solver_be.domain.user.dto.AdminSignupRequestDto;
import com.solver.solver_be.global.util.TimeStamped;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

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

    @Column(nullable = false)
    private String companyName;

    @Column
    private String companyPhoneNum;

    @Column
    private String companyAddress;

    @Column(precision = 10, scale = 7)
    private BigDecimal x;

    @Column(precision = 10, scale = 7)
    private BigDecimal y;

    public static Company of(CompanyRequestDto companyRequestDto) {
        return Company.builder()
                .businessNum(companyRequestDto.getBusinessNum())
                .businessCode(companyRequestDto.getBusinessCode())
                .companyName(companyRequestDto.getCompanyName())
                .companyAddress(companyRequestDto.getCompanyAddress())
                .companyPhoneNum(companyRequestDto.getCompanyPhoneNum())
                .x(BigDecimal.valueOf(companyRequestDto.getX()))
                .y(BigDecimal.valueOf(companyRequestDto.getY()))
                .build();
    }

    public void update(CompanyRequestDto companyRequestDto) {
        this.businessNum = companyRequestDto.getBusinessNum();
        this.businessCode = companyRequestDto.getBusinessCode();
        this.companyName = companyRequestDto.getCompanyName();
        this.companyAddress = companyRequestDto.getCompanyAddress();
        this.companyPhoneNum = companyRequestDto.getCompanyPhoneNum();
        this.x = BigDecimal.valueOf(companyRequestDto.getX());
        this.y = BigDecimal.valueOf(companyRequestDto.getY());
    }
}
