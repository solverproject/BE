package com.solver.solver_be.domain.business.dto;


import com.solver.solver_be.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;

@Getter
public class BusinessResponseDto {

    private String businessNum;

    private String businessCode;

    private String businessName;

    private Integer x;

    private Integer y;

    @Builder
    public BusinessResponseDto(String businessNum, String businessCode, String businessName,
                               Integer x, Integer y){
        this.businessNum = businessNum;
        this.businessCode = businessCode;
        this.businessName = businessName;
        this.x = x;
        this.y = y;
    }

    public static BusinessResponseDto of(User user, String businessCode, Integer x, Integer y) {
        return BusinessResponseDto.builder()
                .businessNum(user.getBusinessNum())
                .businessCode(businessCode)
                .businessName(user.getBusinessName())
                .x(x)
                .y(y)
                .build();
    }
}
