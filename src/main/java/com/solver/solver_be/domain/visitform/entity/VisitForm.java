package com.solver.solver_be.domain.visitform.entity;

import com.solver.solver_be.domain.branch.entity.Branch;
import com.solver.solver_be.domain.user.entity.User;
import com.solver.solver_be.domain.visitform.dto.VisitFormRequestDto;
import com.solver.solver_be.global.util.TimeStamped;
import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class VisitForm extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)           // 방문할 회사
    private String location;

    @Column(nullable = false)           // 방문할 회사의 자세한 공간
    private String place;

    @Column(nullable = false)           // 찾아갈 사람
    private String target;

    @Column(nullable = false)           // 목적
    private String purpose;

    @Column(nullable = false)           // 방문 날짜
    private String startDate;

    @Column(nullable = false)           // 방문 시간
    private String startTime;

    @Column(nullable = false)           // 퇴실 날짜
    private String endDate;

    @Column(nullable = false)           // 퇴실 시간
    private String endTime;

    @Column(nullable = false)           // 승인 여부 -> 이게 대기/삭제/승인
    private String status;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;


    public static VisitForm of(VisitFormRequestDto visitorRequestDto, User user) {
        return VisitForm.builder()
                .location(visitorRequestDto.getLocation())
                .target(visitorRequestDto.getTarget())
                .place(visitorRequestDto.getPlace())
                .purpose(visitorRequestDto.getPurpose())
                .startDate(visitorRequestDto.getStartDate())
                .endDate(visitorRequestDto.getEndDate())
                .startTime(visitorRequestDto.getStartTime())
                .endTime(visitorRequestDto.getEndTime())
                .status(visitorRequestDto.getStatus())
                .user(user)
                .build();
    }

    public void update(VisitFormRequestDto visitorRequestDto) {
        this.location = visitorRequestDto.getLocation();
        this.target = visitorRequestDto.getTarget();
        this.place = visitorRequestDto.getPlace();
        this.purpose = visitorRequestDto.getPurpose();
        this.startDate = visitorRequestDto.getStartDate();
        this.endDate = visitorRequestDto.getEndDate();
        this.startTime = visitorRequestDto.getStartTime();
        this.endTime = visitorRequestDto.getEndTime();
    }
}
