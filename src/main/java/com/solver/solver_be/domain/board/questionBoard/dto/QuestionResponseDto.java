package com.solver.solver_be.domain.board.questionBoard.dto;

import com.solver.solver_be.domain.board.answerBoard.dto.AnswerResponseDto;
import com.solver.solver_be.domain.board.questionBoard.entity.QuestionBoard;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private List<AnswerResponseDto> answerList = new ArrayList<>();


    @Builder
    private QuestionResponseDto(Long id, Long parentBoardId, String title, String contents,
                                List<String> imageUrlList,
                                List<String> titleList, Long x, Long y, LocalDateTime createdAt, LocalDateTime modifiedAt,
                                List<AnswerResponseDto> answerList) {
        this.id = id;
        this.parentBoardId = parentBoardId;
        this.title = title;
        this.contents = contents;
        this.imageUrlList = imageUrlList;
        this.titleList = titleList;
        this.x = x;
        this.y = y;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.answerList = answerList;
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
                .createdAt(questionBoard.getCreatedAt())
                .modifiedAt(questionBoard.getModifiedAt())
                .build();
    }

    public static QuestionResponseDto of(QuestionBoard questionBoard, List<String> titleList, List<String> imageUrlList, List<AnswerResponseDto> answerList) {
        return QuestionResponseDto.builder()
                .id(questionBoard.getId())
                .parentBoardId(questionBoard.getParentBoardId())
                .title(questionBoard.getTitle())
                .contents(questionBoard.getContents())
                .imageUrlList(imageUrlList)
                .titleList(titleList)
                .y(questionBoard.getY())
                .x(questionBoard.getX())
                .createdAt(questionBoard.getCreatedAt())
                .modifiedAt(questionBoard.getModifiedAt())
                .answerList(answerList)
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
