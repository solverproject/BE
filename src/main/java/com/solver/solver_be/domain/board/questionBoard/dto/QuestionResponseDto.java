package com.solver.solver_be.domain.board.questionBoard.dto;

import com.solver.solver_be.domain.board.questionBoard.entity.QuestionBoard;
import com.solver.solver_be.domain.hashtag.entity.HashTag;
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
    private List<String> titleList;

    @Builder
    private QuestionResponseDto(Long id, String title, String contents, List<String> imageUrlList, List<String> titleList) {
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.imageUrlList = imageUrlList;
        this.titleList = titleList;
    }

    public static QuestionResponseDto of(QuestionBoard questionBoard, List<String> titleList, List<String> imageUrlList) {
        return QuestionResponseDto.builder()
                .id(questionBoard.getId())
                .title(questionBoard.getTitle())
                .contents(questionBoard.getContents())
                .imageUrlList(imageUrlList)
                .titleList(titleList)
                .build();
    }

    public static QuestionResponseDto of(QuestionBoard questionBoard, List<String> titleList) {
        return QuestionResponseDto.builder()
                .id(questionBoard.getId())
                .title(questionBoard.getTitle())
                .contents(questionBoard.getContents())
                .titleList(titleList)
                .build();
    }

    public static QuestionResponseDto of(QuestionBoard questionBoard) {
        return QuestionResponseDto.builder()
                .id(questionBoard.getId())
                .title(questionBoard.getTitle())
                .contents(questionBoard.getContents())
                .build();
    }
}
