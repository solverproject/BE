package com.solver.solver_be.domain.user.entity;

import com.solver.solver_be.domain.board.questionBoard.entity.QuestionBoard;
import com.solver.solver_be.domain.favorite.entity.Favorite;
import lombok.*;

import javax.persistence.*;

@Entity(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String userEmail;

    @Column(nullable = false, unique = true)
    private String nickname;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    @Builder
    private User(String userEmail, String password, UserRoleEnum role, String nickname) {
        this.userEmail = userEmail;
        this.password = password;
        this.role = role;
        this.nickname = nickname;
    }

    public static User of(String userEmail, String password, UserRoleEnum role, String nickname) {
        return User.builder()
                .userEmail(userEmail)
                .password(password)
                .role(role)
                .nickname(nickname)
                .build();
    }
}

