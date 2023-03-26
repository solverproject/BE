package com.solver.solver_be.domain.company.entity;

import com.solver.solver_be.domain.company.dto.CompanyRequestDto;
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
    private String businessName;

    @Column
    private Integer x;

    @Column
    private Integer y;

    public static Company of(String businessNum, String businessCode, String businessName, Integer x, Integer y) {
        return Company.builder()
                .businessNum(businessNum)
                .businessCode(businessCode)
                .businessName(businessName)
                .x(x)
                .y(y)
                .build();
    }

    public static Company of(CompanyRequestDto companyRequestDto) {
        return Company.builder()
                .businessNum(companyRequestDto.getBusinessNum())
                .businessCode(companyRequestDto.getBusinessCode())
                .businessName(companyRequestDto.getBusinessName())
                .x(companyRequestDto.getX())
                .y(companyRequestDto.getY())
                .build();
    }

}
