package com.solver.solver_be.domain.mindmap.dto;

import com.solver.solver_be.domain.mindmap.entity.MindMap;
import com.solver.solver_be.domain.questionBoard.dto.QuestionResponseDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class MindMapResponseDto {

    private Long id;
    private String title;
    private List<QuestionResponseDto> questionBoardList;

    @Builder
    private MindMapResponseDto(List<QuestionResponseDto> questionBoardList, Long id, String title){
        this.id = id;
        this.title = title;
        this.questionBoardList = questionBoardList;
    }

    public static MindMapResponseDto of(MindMap mindMap){
        return MindMapResponseDto.builder()
                .id(mindMap.getId())
                .title(mindMap.getTitle())
                .build();
    }

    public static MindMapResponseDto of(MindMap mindMap, List<QuestionResponseDto> questionBoardList){
        return MindMapResponseDto.builder()
                .id(mindMap.getId())
                .title(mindMap.getTitle())
                .questionBoardList(questionBoardList)
                .build();
    }

}
