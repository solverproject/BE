package com.solver.solver_be.domain.bookmark.entity;


import com.solver.solver_be.domain.questionBoard.entity.QuestionBoard;
import com.solver.solver_be.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class BookMark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @ManyToOne
    @JoinColumn(name = "QUESTIONBOARD_ID")
    private QuestionBoard questionBoard;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;


    @Builder
    private BookMark(QuestionBoard questionBoard, User user) {
        this.questionBoard = questionBoard;
        this.user = user;
    }

    public static BookMark of(QuestionBoard questionBoard, User user) {
        return BookMark.builder()
                .questionBoard(questionBoard)
                .user(user)
                .build();
    }
}