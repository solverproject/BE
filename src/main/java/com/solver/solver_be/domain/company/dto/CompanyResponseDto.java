package com.solver.solver_be.domain.company.dto;


import com.solver.solver_be.domain.company.entity.Company;
import com.solver.solver_be.domain.user.entity.Admin;
import com.solver.solver_be.domain.user.entity.Guest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CompanyResponseDto {

    private Long id;

    private String businessNum;

    private String businessCode;

    private String companyName;

    private String companyPhoneNum;

    private String companyAddress;

    private Double x;

    private Double y;

    public static CompanyResponseDto of(Company company) {
        return CompanyResponseDto.builder()
                .id(company.getId())
                .businessNum(company.getBusinessNum())
                .businessCode(company.getBusinessCode())
                .companyName(company.getCompanyName())
                .companyAddress(company.getCompanyAddress())
                .companyPhoneNum(company.getCompanyPhoneNum())
                .x(company.getX().doubleValue())
                .y(company.getY().doubleValue())
                .build();
    }

}
