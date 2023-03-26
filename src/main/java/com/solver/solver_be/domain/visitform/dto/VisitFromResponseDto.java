package com.solver.solver_be.domain.visitform.dto;

import com.solver.solver_be.domain.user.entity.User;
import com.solver.solver_be.domain.visitform.entity.VisitForm;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
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


    @Builder
    private VisitFromResponseDto(Long id, String location, String place, String target, String purpose,
                                 String startDate, String endDate, String startTime, String endTime,
                                 String visitor, String phoneNum) {
        this.id = id;
        this.location = location;
        this.place = place;
        this.target = target;
        this.purpose = purpose;
        this.startDate = startDate;
        this.startTime = startTime;
        this.endDate = endDate;
        this.endTime = endTime;
        this.visitor = visitor;
        this.phoneNum = phoneNum;
    }

    public static VisitFromResponseDto of(VisitForm visitForm, User user){
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
                .visitor(user.getName())
                .phoneNum(user.getPhoneNum())
                .build();
    }
}
