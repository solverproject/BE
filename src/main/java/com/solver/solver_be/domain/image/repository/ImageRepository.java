package com.solver.solver_be.domain.image.repository;

import com.solver.solver_be.domain.image.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findAllByQuestionBoardId(Long id);

    void deleteAllByQuestionBoardId(Long id);
}
