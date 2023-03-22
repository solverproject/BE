package com.solver.solver_be.domain.questionBoard.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class QuestionRequestDto {
    private Long parentBoardId;
    private Long myBoardId;
    private String title;
    private String contents;
    private Long x;
    private Long y;
    private List<String> hashTagList;

}
