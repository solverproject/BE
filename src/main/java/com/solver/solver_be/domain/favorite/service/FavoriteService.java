package com.solver.solver_be.domain.favorite.service;

import com.solver.solver_be.domain.board.questionBoard.entity.QuestionBoard;
import com.solver.solver_be.domain.board.questionBoard.repository.QuestionBoardRepository;
import com.solver.solver_be.domain.favorite.entity.Favorite;
import com.solver.solver_be.domain.favorite.repository.FavoriteRepository;
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
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final QuestionBoardRepository questionBoardRepository;

    @Transactional
    public ResponseEntity<GlobalResponseDto> createFavorite(Long id, User user) {
        QuestionBoard questionBoard = getQuestionBoardById(id);

        boolean favoritePush = false;

        Optional<Favorite> favorite = favoriteRepository.findByQuestionBoardIdAndUserId(id, user.getId());

        if (favorite.isEmpty()) {
            favoriteRepository.saveAndFlush(Favorite.of(questionBoard, user));
            return ResponseEntity.ok(GlobalResponseDto.of(ResponseCode.FAVORITE_SUCCESS, favoritePush));

        }else {
            favoriteRepository.deleteByQuestionBoardIdAndUserId(id, user.getId());
            return ResponseEntity.ok(GlobalResponseDto.of(ResponseCode.FAVORITE_SUCCESS, favoritePush));
        }
    }

    private QuestionBoard getQuestionBoardById(Long id) {
        return questionBoardRepository.findById(id).orElseThrow(
                () -> new QuestionBoardException(ResponseCode.BOARD_NOT_FOUND)
        );
    }

}
