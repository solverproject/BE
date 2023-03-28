package com.solver.solver_be.domain.visitform.dto;


import com.solver.solver_be.domain.visitform.entity.VisitForm;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VisitFormStatusResponseDto {

    private String status;

    public static VisitFormStatusResponseDto of (VisitForm visitForm){
        return VisitFormStatusResponseDto.builder()
                .status(visitForm.getStatus())
                .build();
    }
}
