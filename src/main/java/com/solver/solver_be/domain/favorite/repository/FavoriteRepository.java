package com.solver.solver_be.domain.favorite.repository;

import com.solver.solver_be.domain.favorite.entity.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
}
