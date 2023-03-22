package com.solver.solver_be.domain.answerBoard.dto;

import com.solver.solver_be.domain.answerBoard.entity.AnswerBoard;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class AnswerResponseDto {

    private Long id;
    private String contents;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    @Builder
    private AnswerResponseDto(Long id, String contents, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.contents = contents;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public static AnswerResponseDto of(AnswerBoard answerBoard) {
        return AnswerResponseDto.builder()
                .id(answerBoard.getId())
                .contents(answerBoard.getContents())
                .createdAt(answerBoard.getCreatedAt())
                .modifiedAt(answerBoard.getModifiedAt())
                .build();
    }
}
