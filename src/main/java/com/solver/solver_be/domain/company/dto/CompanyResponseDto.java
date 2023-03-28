package com.solver.solver_be.domain.company.dto;


import com.solver.solver_be.domain.company.entity.Company;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CompanyResponseDto {

    private Long id;

    private String businessNum;

    private String companyName;

    private Integer x;

    private Integer y;

    private String companyToken;


    @Builder
    public CompanyResponseDto(Long id, String businessNum, String companyName,
                              Integer x, Integer y, String companyToken){
        this.id = id;
        this.businessNum = businessNum;
        this.companyName = companyName;
        this.x = x;
        this.y = y;
        this.companyToken = companyToken;
    }


    public static CompanyResponseDto of(Company company) {
        return CompanyResponseDto.builder()
                .id(company.getId())
                .businessNum(company.getBusinessNum())
                .companyName(company.getCompanyName())
                .x(company.getX())
                .y(company.getY())
                .companyToken(company.getCompanyToken())
                .build();
    }
}
