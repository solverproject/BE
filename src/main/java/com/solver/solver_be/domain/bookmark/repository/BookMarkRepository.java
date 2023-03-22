package com.solver.solver_be.domain.bookmark.repository;

import com.solver.solver_be.domain.bookmark.entity.BookMark;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookMarkRepository extends JpaRepository<BookMark, Long> {
    Optional<BookMark> findByQuestionBoardIdAndUserId(Long questionBoardId, Long userId);

    void deleteByQuestionBoardIdAndUserId(Long questionBoardId, Long userId);
}
