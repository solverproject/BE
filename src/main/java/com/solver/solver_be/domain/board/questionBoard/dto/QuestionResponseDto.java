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
    private Long parentBoardId;
    private String title;
    private String contents;
    private List<String> imageUrlList;
    private List<String> titleList;
    private Long x;
    private Long y;

    @Builder
    private QuestionResponseDto(Long id, Long parentBoardId, String title, String contents, List<String> imageUrlList, List<String> titleList, Long x, Long y) {
        this.id = id;
        this.parentBoardId = parentBoardId;
        this.title = title;
        this.contents = contents;
        this.imageUrlList = imageUrlList;
        this.titleList = titleList;
        this.x = x;
        this.y = y;
    }

    public static QuestionResponseDto of(QuestionBoard questionBoard, List<String> titleList, List<String> imageUrlList) {
        return QuestionResponseDto.builder()
                .id(questionBoard.getId())
                .parentBoardId(questionBoard.getParentBoardId())
                .title(questionBoard.getTitle())
                .contents(questionBoard.getContents())
                .imageUrlList(imageUrlList)
                .titleList(titleList)
                .y(questionBoard.getY())
                .x(questionBoard.getX())
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
