package com.solver.solver_be.domain.favorite.entity;


import com.solver.solver_be.domain.board.questionBoard.entity.QuestionBoard;
import com.solver.solver_be.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Favorite {

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
    private Favorite(QuestionBoard questionBoard,User user) {
        this.questionBoard = questionBoard;
        this.user = user;
    }

    public static Favorite of(QuestionBoard questionBoard, User user) {
        return Favorite.builder()
                .questionBoard(questionBoard)
                .user(user)
                .build();
    }
}