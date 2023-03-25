package com.solver.solver_be.domain.manager.entity;

import com.solver.solver_be.domain.department.entity.Department;
import com.solver.solver_be.global.util.TimeStamped;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Manager extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @ManyToOne
    @JoinColumn(name = "DEPARTMENT_ID", nullable = false)
    private Department department;

    @Builder
    private Manager(String name, Department department) {
        this.name = name;
        this.department = department;
    }

    public static Manager of() {
        return Manager.builder()
                .build();
    }

}
