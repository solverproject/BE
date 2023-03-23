package com.solver.solver_be.domain.mindmap.dto;

import com.solver.solver_be.domain.mindmap.entity.MindMap;
import com.solver.solver_be.domain.questionBoard.dto.QuestionBoardResponseDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class MindMapResponseDto {

    private Long mindMapId;
    private String mindMapTitle;
    private List<QuestionBoardResponseDto> questionBoardList;

    @Builder
    private MindMapResponseDto(List<QuestionBoardResponseDto> questionBoardResponseDtoList, Long id, String title){
        this.mindMapId = id;
        this.mindMapTitle = title;
        this.questionBoardList = questionBoardResponseDtoList;
    }

    public static MindMapResponseDto of(MindMap mindMap){
        return MindMapResponseDto.builder()
                .id(mindMap.getId())
                .title(mindMap.getTitle())
                .build();
    }


    public static MindMapResponseDto of(MindMap mindMap, List<QuestionBoardResponseDto> questionBoardResponseDtoList){
        return MindMapResponseDto.builder()
                .id(mindMap.getId())
                .title(mindMap.getTitle())
                .questionBoardResponseDtoList(questionBoardResponseDtoList)
                .build();
    }
}
