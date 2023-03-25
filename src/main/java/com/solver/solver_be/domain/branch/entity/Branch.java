package com.solver.solver_be.domain.branch.entity;

import com.solver.solver_be.domain.department.entity.Department;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Branch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 1. 건물명
    @Column
    private String title;

    // 2. 건물주소
    @Column
    private String address;

    // 3. 보안부서
    @ManyToOne
    @JoinColumn(name = "DEPARTMENT_ID", nullable = false)
    private Department department;


    @Builder
    private Branch(String title, String address, Department department) {
        this.title = title;
        this.address = address;
        this.department = department;
    }

    public static Branch of() {
        return Branch.builder()

                .build();
    }

}
