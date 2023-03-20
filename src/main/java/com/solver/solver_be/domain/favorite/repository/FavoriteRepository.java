package com.solver.solver_be.domain.favorite.repository;

import com.solver.solver_be.domain.favorite.entity.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    Optional<Favorite> findByQuestionBoardAndUserId(Long questionBoardId, Long userId);

    void deleteByByQuestionBoardAndUserId(Long questionBoardId, Long userId);
}
