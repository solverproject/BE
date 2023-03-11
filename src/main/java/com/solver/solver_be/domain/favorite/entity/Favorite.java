package com.solver.solver_be.domain.favorite.entity;


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
    @JoinColumn(name = "USER_ID")
    private User user;

    @Builder
    private Favorite(User user) {
        this.user = user;
    }

    public static Favorite from(User user) {
        return Favorite.builder()
                .user(user)
                .build();
    }
}
