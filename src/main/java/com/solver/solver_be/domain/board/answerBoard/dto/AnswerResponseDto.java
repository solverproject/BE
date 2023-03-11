package com.solver.solver_be.domain.board.answerBoard.dto;

import com.solver.solver_be.domain.board.answerBoard.entity.AnswerBoard;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AnswerResponseDto {

    private String contents;

    @Builder
    private AnswerResponseDto(String contents) {
        this.contents = contents;
    }

    public static AnswerResponseDto of(AnswerBoard answerBoard) {
        return AnswerResponseDto.builder()
                .contents(answerBoard.getContents())
                .build();
    }
}
