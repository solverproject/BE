package com.solver.solver_be.domain.company.dto;


import com.solver.solver_be.domain.company.entity.Company;
import com.solver.solver_be.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CompanyResponseDto {

    private String businessNum;

    private String businessCode;

    private String businessName;

    private Integer x;

    private Integer y;

    @Builder
    public CompanyResponseDto(String businessNum, String businessCode, String businessName,
                              Integer x, Integer y){
        this.businessNum = businessNum;
        this.businessCode = businessCode;
        this.businessName = businessName;
        this.x = x;
        this.y = y;
    }

    public static CompanyResponseDto of(User user, String businessCode, Integer x, Integer y) {
        return CompanyResponseDto.builder()
                .businessNum(user.getBusinessNum())
                .businessCode(businessCode)
                .businessName(user.getBusinessName())
                .x(x)
                .y(y)
                .build();
    }

    public static CompanyResponseDto of(Company company) {
        return CompanyResponseDto.builder()
                .businessNum(company.getBusinessNum())
                .businessCode(company.getBusinessCode())
                .businessName(company.getBusinessName())
                .x(company.getX())
                .y(company.getY())
                .build();
    }
}
