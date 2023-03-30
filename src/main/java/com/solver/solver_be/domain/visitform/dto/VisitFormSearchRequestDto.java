package com.solver.solver_be.domain.visitform.dto;

import lombok.Getter;

@Getter
public class VisitFormSearchRequestDto {
    private String guestName;
    private String location;
    private String target;
    private String startDate;
    private String endDate;
    private String purpose;
    private String status;
}
