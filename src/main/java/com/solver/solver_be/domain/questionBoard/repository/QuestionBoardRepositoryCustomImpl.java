package com.solver.solver_be.domain.questionBoard.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.solver.solver_be.domain.questionBoard.entity.QuestionBoard;

import javax.persistence.EntityManager;

import java.util.List;

import static com.solver.solver_be.domain.questionBoard.entity.QQuestionBoard.questionBoard;


public class QuestionBoardRepositoryCustomImpl implements CustomQuestionBoardRepository {

    private final JPAQueryFactory queryFactory;

    public QuestionBoardRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<QuestionBoard> findQuestionBoardListByContentsContains(String keyword) {
        return queryFactory.selectFrom(questionBoard)
                .where(questionBoard.contents.contains(keyword))
                .fetch();
    }
}
