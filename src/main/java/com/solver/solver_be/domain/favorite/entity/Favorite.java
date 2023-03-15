package com.solver.solver_be.domain.favorite.entity;


import com.solver.solver_be.domain.board.questionBoard.entity.QuestionBoard;
import com.solver.solver_be.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

// 내가 질문1게시글에 즐겨찾기를 함 -> 내 id가 이 게시물에 남아있어야함.
// 내가 favorite 테이블에 남기고싶은게 무엇인가? => 1. 이 게시물을 즐겨찾기 한 사람. // 2. 어떤 테이블인지.
// 질문 1게시글 (id = 1) , userId = 3 즐겨찾기를 함.
// 다른 사람인 userId= 4 인 애가 또 즐겨찾기를 했어, -> BoardId = 1 => userId = 1, 3
// Favorite table 에는 지금 3가지 : 얘의 개인 id, 어떠한 게시물의 id 값, 그 게시물을 좋아한 userIDList