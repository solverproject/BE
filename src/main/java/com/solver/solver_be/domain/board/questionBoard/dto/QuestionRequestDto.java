package com.solver.solver_be.domain.board.questionBoard.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class QuestionRequestDto {
    private String title;
    private String contents;

}
