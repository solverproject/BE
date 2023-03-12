package com.solver.solver_be.domain.board.questionBoard.service;

import com.solver.solver_be.domain.board.questionBoard.dto.QuestionRequestDto;
import com.solver.solver_be.domain.board.questionBoard.dto.QuestionResponseDto;
import com.solver.solver_be.domain.board.questionBoard.entity.QuestionBoard;
import com.solver.solver_be.domain.board.questionBoard.repository.QuestionBoardRepository;
import com.solver.solver_be.domain.image.entity.Image;
import com.solver.solver_be.domain.image.repository.ImageRepository;
import com.solver.solver_be.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class QuestionService {

    private final QuestionBoardRepository questionBoardRepository;
    private final ImageRepository imageRepository;

    public ResponseEntity<QuestionResponseDto> createBoard(QuestionRequestDto requestDto, User user){

        QuestionBoard questionBoard = questionBoardRepository.saveAndFlush(QuestionBoard.of(requestDto, user));

        List<String> imageList = new ArrayList<>();

        for(String image : imageList){
            Optional<Image> image = imageRepository.findById(user.getId());
        }

        return ResponseEntity.ok(QuestionResponseDto.of(questionBoard, image.get());
    }

    public ResponseEntity<QuestionResponseDto> updateBoard(Long id, QuestionRequestDto requestDto, User user){
        Optional<QuestionBoard> questionBoard = questionBoardRepository.findById(id);

        if(!questionBoard.get().getUser().equals(user)){
            throw new IllegalArgumentException("사용자가 맞지 않습니다.");
        }

        if(questionBoard.isEmpty()){
            throw new IllegalArgumentException("게시판이 없습니다.");
        }

        questionBoard.get().update(requestDto);

        QuestionResponseDto questionResponseDto = QuestionResponseDto.of(questionBoard.get());

        return ResponseEntity.ok(QuestionResponseDto.of());
    }

    public ResponseEntity<Void> deleteBoard(Long id, User user){
        Optional<QuestionBoard> questionBoard = questionBoardRepository.findById(id);
        if(questionBoard.isEmpty()){
            throw new IllegalArgumentException("게시판이 없습니다.");
        }

        questionBoardRepository.deleteById(questionBoard.get().getId());
        return new ResponseEntity.ok().body();
    }
}
