package com.solver.solver_be.domain.favorite.service;

import com.solver.solver_be.domain.board.questionBoard.entity.QuestionBoard;
import com.solver.solver_be.domain.board.questionBoard.repository.QuestionBoardRepository;
import com.solver.solver_be.domain.favorite.entity.Favorite;
import com.solver.solver_be.domain.favorite.repository.FavoriteRepository;
import com.solver.solver_be.domain.user.entity.User;
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
    public ResponseEntity<GlobalResponseDto> createFavorite(Long id, User user){
        QuestionBoard questionBoard = questionBoardRepository.findById(id).orElseThrow(
                () -> new FavoriteException(ResponseCode.)
        );

        Optional<Favorite> favorite = favoriteRepository.findByUserIdAndQuestionBoard(id, user.getId());
        if (favorite.isEmpty()) {
            favoriteRepository.saveAndFlush(Favorite.from(user));
            return ResponseEntity.ok(GlobalResponseDto.of(ResponseCode.));

        }else {
            favoriteRepository.deleteByUserIdAndQuestionBoard(user.getId(), id);
            return ResponseEntity.ok(GlobalResponseDto.of(ResponseCode.));
        }
    }
}
