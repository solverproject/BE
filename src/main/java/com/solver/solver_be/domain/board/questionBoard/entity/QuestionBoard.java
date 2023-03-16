package com.solver.solver_be.domain.board.questionBoard.entity;

import com.solver.solver_be.domain.board.questionBoard.dto.QuestionRequestDto;
import com.solver.solver_be.domain.user.entity.User;
import com.solver.solver_be.global.util.TimeStamped;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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
    // 이 부분을 나중에 QuestionBoardDto 로 받는 부분으로 고치면 좋을 듯 합니다.
    public static QuestionBoard of(String title, String contents, User user){
        return builder()
                .title(title)
                .contents(contents)
                .user(user)
                .build();
    }

    public void update(QuestionRequestDto questionRequestDto){
        this.contents = questionRequestDto.getContents();
    }

}
