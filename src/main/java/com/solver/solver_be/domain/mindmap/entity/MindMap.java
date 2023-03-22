package com.solver.solver_be.domain.mindmap.entity;

import com.solver.solver_be.domain.mindmap.dto.MindMapRequestDto;
import com.solver.solver_be.domain.user.entity.User;
import com.solver.solver_be.domain.workspace.entity.WorkSpace;
import com.solver.solver_be.global.util.TimeStamped;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MindMap extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne
    @JoinColumn(name = "WORKSPACE_ID")
    private WorkSpace workSpace;

    @Builder
    private MindMap(WorkSpace workSpace, User user, String title){
        this.title = title;
        this.workSpace = workSpace;
        this.user = user;
    }

    public static MindMap of(MindMapRequestDto mindMapRequestDto,User user){
        return MindMap.builder()
                .title(mindMapRequestDto.getTitle())
                .user(user)
                .build();
    }

    public static MindMap of(MindMapRequestDto mindMapRequestDto, WorkSpace workSpace, User user){
        return MindMap.builder()
                .title(mindMapRequestDto.getTitle())
                .workSpace(workSpace)
                .user(user)
                .build();
    }

    public MindMap updateMindMap(MindMapRequestDto mindMapRequestDto){
        this.title = mindMapRequestDto.getTitle();
        return this;
    }
}
