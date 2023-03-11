package com.solver.solver_be.domain.image.repository;

import com.solver.solver_be.domain.image.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
