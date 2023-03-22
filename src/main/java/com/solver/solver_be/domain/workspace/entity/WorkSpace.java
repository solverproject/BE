package com.solver.solver_be.domain.workspace.entity;

import com.solver.solver_be.domain.user.entity.User;
import com.solver.solver_be.domain.workspace.dto.WorkSpaceRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class WorkSpace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private Boolean isAdmin;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @Builder
    private WorkSpace(String title,Boolean isAdmin,User user) {
        this.title = title;
        this.user = user;
        this.isAdmin = isAdmin;
    }

    public static WorkSpace of(WorkSpaceRequestDto workSpaceRequestDto, User user) {
        return WorkSpace.builder()
                .title(workSpaceRequestDto.getTitle())
                .user(user)
                .isAdmin(true)
                .build();
    }

}
