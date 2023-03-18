package com.solver.solver_be.domain.board.answerBoard.controller;

import com.solver.solver_be.domain.board.answerBoard.dto.AnswerRequestDto;
import com.solver.solver_be.domain.board.answerBoard.service.AnswerService;
import com.solver.solver_be.global.response.GlobalResponseDto;
import com.solver.solver_be.global.security.webSecurity.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class AnswerController {

    private final AnswerService answerService;

    @PostMapping("/answers/{id}")
    public ResponseEntity<GlobalResponseDto> createAnswer(@PathVariable Long id,
                                                          @RequestPart(value = "data")AnswerRequestDto answerRequestDto,
                                                          @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {
        return answerService.createAnswer(id, answerRequestDto, userDetails.getUser());
    }

    @PutMapping("/answer/{id}")
    public ResponseEntity<GlobalResponseDto> updateAnswer(@PathVariable Long id,
                                                          @RequestBody AnswerRequestDto answerRequestDto,
                                                          @AuthenticationPrincipal UserDetailsImpl userDetails){
        return answerService.updateAnswer(id, answerRequestDto, userDetails.getUser());
    }

    @DeleteMapping("/answer/{id}")
    public ResponseEntity<GlobalResponseDto> deleteAnswer(@PathVariable Long id,
                                                          @AuthenticationPrincipal UserDetailsImpl userDetails){
        return answerService.deleteAnswer(id, userDetails.getUser());
    }
}
