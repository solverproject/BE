package com.solver.solver_be.domain.visitform.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccessStatusResponseDto {

    private String date;
    private Long applyNumber;
    private Long approveNumber;
    private Long sumNumber;

    public static AccessStatusResponseDto of(String date, Long applyNumber, Long approveNumber, Long sumNumber) {
        return AccessStatusResponseDto.builder()
                .applyNumber(applyNumber)
                .approveNumber(approveNumber)
                .date(date)
                .sumNumber(sumNumber)
                .build();

    }
}
