package com.solver.solver_be.domain.company.dto;

import lombok.Getter;

import javax.persistence.Column;

@Getter
public class CompanyRequestDto {

    private String businessNum;

    private String businessCode;

    private String companyName;

    private String companyPhoneNum;

    private String companyAddress;

    private Double x;

    private Double y;

}
