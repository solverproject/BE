package com.solver.solver_be.domain.questionBoard.repository;

import com.solver.solver_be.domain.questionBoard.entity.QuestionBoard;

import java.util.List;

public interface CustomQuestionBoardRepository {
    List<QuestionBoard> findQuestionBoardListByContentsContains(String keyword);
}
