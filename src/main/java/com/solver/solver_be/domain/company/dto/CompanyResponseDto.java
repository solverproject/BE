package com.solver.solver_be.domain.company.dto;


import com.solver.solver_be.domain.company.entity.Company;
import com.solver.solver_be.domain.user.entity.Admin;
import com.solver.solver_be.domain.user.entity.Guest;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CompanyResponseDto {

    private Long id;

    private String businessNum;

    private String businessCode;

    private String companyName;

    private Integer x;

    private Integer y;

    @Builder
    public CompanyResponseDto(Long id, String businessNum, String businessCode, String companyName,
                              Integer x, Integer y){
        this.id = id;
        this.businessNum = businessNum;
        this.businessCode = businessCode;
        this.companyName = companyName;
        this.x = x;
        this.y = y;
    }


    public static CompanyResponseDto of(Company company) {
        return CompanyResponseDto.builder()
                .id(company.getId())
                .businessNum(company.getBusinessNum())
                .businessCode(company.getBusinessCode())
                .companyName(company.getCompanyName())
                .x(company.getX())
                .y(company.getY())
                .build();
    }
}
