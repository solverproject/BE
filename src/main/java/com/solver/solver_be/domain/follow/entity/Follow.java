package com.solver.solver_be.domain.follow.entity;

import com.solver.solver_be.domain.user.entity.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Follow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @Builder
    private Follow(User user) {
        this.user = user;
    }

    public static Follow of(User user){
        return Follow.builder()
                .user(user)
                .build();
    }
}
