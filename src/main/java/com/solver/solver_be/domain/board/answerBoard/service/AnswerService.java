package com.solver.solver_be.domain.board.answerBoard.service;

import com.solver.solver_be.domain.board.answerBoard.dto.AnswerRequestDto;
import com.solver.solver_be.domain.board.answerBoard.dto.AnswerResponseDto;
import com.solver.solver_be.domain.board.answerBoard.entity.AnswerBoard;
import com.solver.solver_be.domain.board.answerBoard.repository.AnswerBoardRepository;
import com.solver.solver_be.domain.board.questionBoard.entity.QuestionBoard;
import com.solver.solver_be.domain.board.questionBoard.repository.QuestionBoardRepository;
import com.solver.solver_be.domain.user.entity.User;
import com.solver.solver_be.global.exception.exceptionType.AnswerBoardException;
import com.solver.solver_be.global.response.GlobalResponseDto;
import com.solver.solver_be.global.response.ResponseCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AnswerService {

        private final AnswerBoardRepository answerBoardRepository;
        private final QuestionBoardRepository questionBoardRepository;

        @Transactional
        public ResponseEntity<GlobalResponseDto> createAnswer(Long id, AnswerRequestDto answerRequestDto, User user) throws IOException {

            QuestionBoard questionBoard = getQuestionBoardById(id);
            AnswerBoard answerBoard = answerBoardRepository.save(AnswerBoard.of(questionBoard, user, answerRequestDto));
            return ResponseEntity.ok(GlobalResponseDto.of(ResponseCode.ANSWER_UPLOAD_SUCCESS, AnswerResponseDto.of(answerBoard)));
        }

        @Transactional
        public ResponseEntity<GlobalResponseDto> updateAnswer(Long id, AnswerRequestDto answerRequestDto, User user) {

            AnswerBoard answerBoard = getAnswerBoardById(id);

            if (!answerBoard.getUser().equals(user)) {
                throw new AnswerBoardException(ResponseCode.ANSWER_UPDATE_FAILED);
            }
            answerBoard.updateAnswer(answerRequestDto);
            return ResponseEntity.ok(GlobalResponseDto.of(ResponseCode.ANSWER_UPDATE_SUCCESS, AnswerResponseDto.of(answerBoard)));
        }

        @Transactional
        public ResponseEntity<GlobalResponseDto> deleteAnswer(Long id, User user){

            AnswerBoard answerBoard = getAnswerBoardById(id);
            if (!answerBoard.getUser().equals(user)) {
                throw new AnswerBoardException(ResponseCode.ANSWER_UPDATE_FAILED);
            }
            answerBoardRepository.deleteById(id);
            return ResponseEntity.ok(GlobalResponseDto.of(ResponseCode.ANSWER_DELETE_SUCCESS));
        }

        private QuestionBoard getQuestionBoardById(Long id) {
                return questionBoardRepository.findById(id).orElseThrow(() -> new AnswerBoardException(ResponseCode.BOARD_NOT_FOUND));
        }

        private AnswerBoard getAnswerBoardById(Long id) {
            return answerBoardRepository.findById(id).orElseThrow(() -> new AnswerBoardException(ResponseCode.ANSWER_NOT_FOUND));
        }
}
