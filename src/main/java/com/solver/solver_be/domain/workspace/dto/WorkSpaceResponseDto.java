package com.solver.solver_be.domain.workspace.dto;

import com.solver.solver_be.domain.mindmap.entity.MindMap;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class WorkSpaceResponseDto {

    private Long id;
    private String title;
    private List<MindMap> mindMapList;
}
