package com.solver.solver_be.domain.bookmark.service;

import com.solver.solver_be.domain.questionBoard.entity.QuestionBoard;
import com.solver.solver_be.domain.questionBoard.repository.QuestionBoardRepository;
import com.solver.solver_be.domain.bookmark.entity.BookMark;
import com.solver.solver_be.domain.bookmark.repository.BookMarkRepository;
import com.solver.solver_be.domain.user.entity.User;
import com.solver.solver_be.global.exception.exceptionType.QuestionBoardException;
import com.solver.solver_be.global.response.GlobalResponseDto;
import com.solver.solver_be.global.response.ResponseCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookMarkService {

    private final BookMarkRepository bookMarkRepository;
    private final QuestionBoardRepository questionBoardRepository;

    @Transactional
    public ResponseEntity<GlobalResponseDto> createFavorite(Long id, User user) {
        QuestionBoard questionBoard = getQuestionBoardById(id);

        boolean favoritePush = false;

        Optional<BookMark> favorite = bookMarkRepository.findByQuestionBoardIdAndUserId(id, user.getId());

        if (favorite.isEmpty()) {
            bookMarkRepository.saveAndFlush(BookMark.of(questionBoard, user));
            return ResponseEntity.ok(GlobalResponseDto.of(ResponseCode.FAVORITE_SUCCESS, favoritePush));

        }else {
            bookMarkRepository.deleteByQuestionBoardIdAndUserId(id, user.getId());
            return ResponseEntity.ok(GlobalResponseDto.of(ResponseCode.FAVORITE_SUCCESS, favoritePush));
        }
    }

    private QuestionBoard getQuestionBoardById(Long id) {
        return questionBoardRepository.findById(id).orElseThrow(
                () -> new QuestionBoardException(ResponseCode.BOARD_NOT_FOUND)
        );
    }

}
