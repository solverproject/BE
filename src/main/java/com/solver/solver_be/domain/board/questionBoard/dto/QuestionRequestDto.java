package com.solver.solver_be.domain.board.questionBoard.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class QuestionRequestDto {
    private String title;
    private String contents;
    private List<String> hashTagList;

}
