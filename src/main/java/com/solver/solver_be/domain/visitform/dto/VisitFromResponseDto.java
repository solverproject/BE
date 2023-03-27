package com.solver.solver_be.domain.visitform.dto;

import com.solver.solver_be.domain.user.entity.Guest;
import com.solver.solver_be.domain.visitform.entity.VisitForm;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VisitFromResponseDto {

    private Long id;
    private String location;
    private String place;
    private String target;
    private String purpose;
    private String startDate;
    private String startTime;
    private String endDate;
    private String endTime;
    private String visitor;
    private String phoneNum;
    private String status;
    public static VisitFromResponseDto of(VisitForm visitForm){
        return VisitFromResponseDto.builder()
                .id(visitForm.getId())
                .location(visitForm.getLocation())
                .target(visitForm.getTarget())
                .place(visitForm.getPlace())
                .purpose(visitForm.getPurpose())
                .startDate(visitForm.getStartDate())
                .startTime(visitForm.getStartTime())
                .endDate(visitForm.getEndDate())
                .endTime(visitForm.getEndTime())
                .visitor(visitForm.getGuest().getName())
                .phoneNum(visitForm.getGuest().getPhoneNum())
                .status(visitForm.getStatus())
                .build();
    }
    public static VisitFromResponseDto of(VisitForm visitForm, Guest guest){
        return VisitFromResponseDto.builder()
                .id(visitForm.getId())
                .location(visitForm.getLocation())
                .target(visitForm.getTarget())
                .place(visitForm.getPlace())
                .purpose(visitForm.getPurpose())
                .startDate(visitForm.getStartDate())
                .startTime(visitForm.getStartTime())
                .endDate(visitForm.getEndDate())
                .endTime(visitForm.getEndTime())
                .visitor(guest.getName())
                .phoneNum(guest.getPhoneNum())
                .status(visitForm.getStatus())
                .build();
    }

}
