package com.solver.solver_be.domain.AccessRecord.entity;

import com.solver.solver_be.domain.user.entity.Guest;
import com.solver.solver_be.domain.visitform.entity.VisitForm;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class AccessRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime inTime;

    @Column(unique = true)
    private LocalDateTime outTime;

    @ManyToOne
    @JoinColumn(name = "GUEST_ID", nullable = false)
    private Guest guest;

    @ManyToOne
    @JoinColumn(name = "VISITFORM_ID", nullable = false)
    private VisitForm visitForm;

    public static AccessRecord of(LocalDateTime inTime, LocalDateTime outTime, Guest guest, VisitForm visitForm) {
        return AccessRecord.builder()
                .inTime(inTime)
                .outTime(outTime)
                .guest(guest)
                .visitForm(visitForm)
                .build();
    }


}
