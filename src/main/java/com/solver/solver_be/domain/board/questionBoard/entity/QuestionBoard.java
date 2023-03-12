package com.solver.solver_be.domain.board.questionBoard.entity;

import com.solver.solver_be.domain.board.questionBoard.dto.QuestionRequestDto;
import com.solver.solver_be.domain.board.questionBoard.dto.QuestionResponseDto;
import com.solver.solver_be.domain.image.entity.Image;
import com.solver.solver_be.domain.user.entity.User;
import com.solver.solver_be.global.util.TimeStamped;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QuestionBoard extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String contents;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @Builder
    private QuestionBoard(String title, String contents, User user){
        this.title = title;
        this.contents = contents;
        this.user = user;
    }

    public static QuestionBoard of(QuestionRequestDto questionRequestDto, User user){
        return builder()
                .title(questionRequestDto.getTitle())
                .contents(questionRequestDto.getContents())
                .user(user)
                .build();
    }

    public void update(QuestionRequestDto questionRequestDto){
        this.contents = questionRequestDto.getContents();
    }

}
