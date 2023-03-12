package com.solver.solver_be.domain.board.questionBoard.dto;


import com.solver.solver_be.domain.board.questionBoard.entity.QuestionBoard;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class QuestionResponseDto {

    private Long id;
    private String title;
    private String contents;
    private List<String> imageUrlList;

    @Builder
    private QuestionResponseDto(Long id, String title, String contents, List<String> imageUrlList){
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.imageUrlList = imageUrlList;
    }

    public static QuestionResponseDto of(QuestionBoard questionBoard, List<String> imageUrlList){
        return QuestionResponseDto.builder()
                .id(questionBoard.getId())
                .title(questionBoard.getTitle())
                .contents(questionBoard.getContents())
                .imageUrlList(imageUrlList)
                .build();
    }

    public static QuestionResponseDto of(QuestionBoard questionBoard){
        return QuestionResponseDto.builder()
                .id(questionBoard.getId())
                .title(questionBoard.getTitle())
                .contents(questionBoard.getContents())
                .build();
    }
}
