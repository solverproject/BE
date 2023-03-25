package com.solver.solver_be.domain.space.entity;

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
public class Space extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String authority;

    @ManyToOne
    @JoinColumn(name = "BRANCH_ID", nullable = false)
    private Branch branch;

    @Builder
    private Space(String name,String authority,Branch branch){
        this.name = name;
        this.authority = authority;
        this.branch = branch;
    }

    public static Space of(){
        return Space.builder()
                .build();
    }

}
