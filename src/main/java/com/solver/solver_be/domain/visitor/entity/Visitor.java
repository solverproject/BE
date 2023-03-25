package com.solver.solver_be.domain.visitor.entity;

import com.solver.solver_be.domain.branch.entity.Branch;
import com.solver.solver_be.global.util.TimeStamped;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Visitor extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String phoneNumber;

    @Column
    private String purpose;

    @Column
    private String visitDate;

    @ManyToOne
    @JoinColumn(name = "BRANCH_ID", nullable = false)
    private Branch branch;


    @Builder
    private Visitor(String name, String phoneNumber, String purpose, String visitDate, Branch branch) {
        this.name = name;
        this.purpose = purpose;
        this.phoneNumber = phoneNumber;
        this.visitDate = visitDate;
        this.branch = branch;
    }

    public static Visitor of() {
        return Visitor.builder()
                .build();
    }
}
