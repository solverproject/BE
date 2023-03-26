package com.solver.solver_be.domain.visitform.entity;

import com.solver.solver_be.domain.branch.entity.Branch;
import com.solver.solver_be.domain.user.entity.User;
import com.solver.solver_be.domain.visitform.dto.VisitFormRequestDto;
import com.solver.solver_be.global.util.TimeStamped;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class VisitForm extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private String place;

    @Column(nullable = false)
    private String target;

    @Column(nullable = false)
    private String purpose;

    @Column(nullable = false)
    private String startDate;

    @Column(nullable = false)
    private String startTime;

    @Column(nullable = false)
    private String endDate;

    @Column(nullable = false)
    private String endTime;

    @Column(nullable = false)
    private String visitor;

    @Column(nullable = false)
    private String phoneNum;

    @Column(nullable = false)
    private Boolean isSign;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;


    @Builder
    private VisitForm(String location, String place, String target, String purpose,
                      String startDate, String endDate, String startTime, String endTime, String visitor, String phoneNum,
                      Boolean isSign, User user) {
        this.location = location;
        this.target = target;
        this.place = place;
        this.purpose = purpose;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.visitor = visitor;
        this.phoneNum = phoneNum;
        this.isSign = isSign;
        this.user = user;
    }

    public static VisitForm of(VisitFormRequestDto visitorRequestDto, Branch branch, User user) {
        return VisitForm.builder()
                .location(visitorRequestDto.getLocation())
                .target(visitorRequestDto.getTarget())
                .place(visitorRequestDto.getPlace())
                .purpose(visitorRequestDto.getPurpose())
                .startDate(visitorRequestDto.getStartDate())
                .endDate(visitorRequestDto.getEndDate())
                .startTime(visitorRequestDto.getStartTime())
                .endTime(visitorRequestDto.getEndTime())
                .visitor(visitorRequestDto.getVisitor())
                .phoneNum(visitorRequestDto.getPhoneNum())
                .isSign(false)
                .user(user)
                .build();
    }

    public void update(VisitFormRequestDto visitorRequestDto){
        this.location = visitorRequestDto.getLocation();
        this.target = visitorRequestDto.getTarget();
        this.place = visitorRequestDto.getPlace();
        this.purpose = visitorRequestDto.getPurpose();
        this.startDate = visitorRequestDto.getStartDate();
        this.endDate = visitorRequestDto.getEndDate();
        this.startTime = visitorRequestDto.getStartTime();
        this.endTime = visitorRequestDto.getEndTime();
        this.visitor = visitorRequestDto.getVisitor();
        this.phoneNum = visitorRequestDto.getPhoneNum();
    }
}
