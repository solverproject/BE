package com.solver.solver_be.domain.workspace.dto;

import com.solver.solver_be.domain.answerBoard.dto.AnswerResponseDto;
import com.solver.solver_be.domain.answerBoard.entity.AnswerBoard;
import com.solver.solver_be.domain.mindmap.entity.MindMap;
import com.solver.solver_be.domain.questionBoard.entity.QuestionBoard;
import com.solver.solver_be.domain.workspace.entity.WorkSpace;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class WorkSpaceResponseDto {

    private Long workSpaceId;
    private String workSpaceTitle;
    private MindMap mindMap;
    private QuestionBoard questionBoard;

    @Builder
    private WorkSpaceResponseDto(Long workSpaceId, String workSpaceTitle,MindMap mindMap, QuestionBoard questionBoard) {
        this.mindMap = mindMap;
        this.workSpaceId = workSpaceId;
        this.workSpaceTitle = workSpaceTitle;
        this.questionBoard = questionBoard;
    }

    public static WorkSpaceResponseDto of(WorkSpace workSpace,MindMap mindMap, QuestionBoard questionBoard) {
        return WorkSpaceResponseDto.builder()
                .workSpaceId(workSpace.getId())
                .workSpaceTitle(workSpace.getTitle())
                .questionBoard(questionBoard)
                .mindMap(mindMap)
                .build();
    }
}
