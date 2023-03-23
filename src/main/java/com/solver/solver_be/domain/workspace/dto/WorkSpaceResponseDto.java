package com.solver.solver_be.domain.workspace.dto;

import com.solver.solver_be.domain.answerBoard.dto.AnswerResponseDto;
import com.solver.solver_be.domain.answerBoard.entity.AnswerBoard;
import com.solver.solver_be.domain.mindmap.entity.MindMap;
import com.solver.solver_be.domain.questionBoard.dto.QuestionResponseDto;
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
    private List<MindMap> mindMapList;
    private List<QuestionBoard> questionBoardList;

    @Builder
    private WorkSpaceResponseDto(Long workSpaceId, String workSpaceTitle, List<MindMap> mindMapList, List<QuestionBoard> questionBoardList) {
        this.mindMapList = mindMapList;
        this.workSpaceId = workSpaceId;
        this.workSpaceTitle = workSpaceTitle;
        this.questionBoardList = questionBoardList;
    }

    public static WorkSpaceResponseDto of(WorkSpace workSpace, List<MindMap> mindMapList, List<QuestionBoard> questionBoardList) {
        return WorkSpaceResponseDto.builder()
                .workSpaceId(workSpace.getId())
                .workSpaceTitle(workSpace.getTitle())
                .mindMapList(mindMapList)
                .questionBoardList(questionBoardList)
                .build();
    }

//    public static WorkSpaceResponseDto of(List<QuestionResponseDto> questionResponseDtoList) {
//        return WorkSpaceResponseDto.builder()
//                .workSpaceId(workSpace.getId())
//                .workSpaceTitle(workSpace.getTitle())
//                .mindMapList(mindMapList)
//                .questionBoardList(questionBoardList)
//                .build();
//    }
}
