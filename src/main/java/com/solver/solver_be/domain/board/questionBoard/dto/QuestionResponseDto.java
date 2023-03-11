package com.solver.solver_be.domain.board.questionBoard.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class QuestionResponseDto {

    private String title;
    private String contents;
    private List<String> imageUrlList;

    @Builder
    private QuestionResponseDto(String title, String contents, List<String> imageUrlList){
        this.title = title;
        this.contents = contents;
        this.imageUrlList = imageUrlList;
    }

    public static QuestionResponseDto of(QuestionRequestDto questionRequestDto, List<String> imageUrlList){
        return QuestionResponseDto.builder()
                .title(questionRequestDto.getTitle())
                .contents(questionRequestDto.getContents())
                .imageUrlList(imageUrlList)
                .build();
    }
}
