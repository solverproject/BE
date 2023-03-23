package com.solver.solver_be.domain.questionBoard.dto;

import com.solver.solver_be.domain.questionBoard.entity.QuestionBoard;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class QuestionBoardResponseDto {

    private Long id;
    private Long parentBoardId;
    private String title;
    private List<String> titleList;
    private Long x;
    private Long y;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    @Builder
    private QuestionBoardResponseDto(Long id, Long parentBoardId, String title,
                                List<String> titleList, Long x, Long y, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.parentBoardId = parentBoardId;
        this.title = title;
        this.titleList = titleList;
        this.x = x;
        this.y = y;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public static QuestionBoardResponseDto of(QuestionBoard questionBoard, List<String> titleList) {
        return QuestionBoardResponseDto.builder()
                .id(questionBoard.getId())
                .parentBoardId(questionBoard.getParentBoardId())
                .title(questionBoard.getTitle())
                .x(questionBoard.getX())
                .y(questionBoard.getY())
                .createdAt(questionBoard.getCreatedAt())
                .modifiedAt(questionBoard.getModifiedAt())
                .titleList(titleList)
                .build();
    }
}
