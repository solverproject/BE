package com.solver.solver_be.domain.board.questionBoard.controller;


import com.solver.solver_be.domain.board.questionBoard.dto.QuestionRequestDto;
import com.solver.solver_be.domain.board.questionBoard.dto.QuestionResponseDto;
import com.solver.solver_be.domain.board.questionBoard.service.QuestionService;
import com.solver.solver_be.global.security.webSecurity.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class QusetionController {

    private final QuestionService questionService;

    @PostMapping("/board/create")
    public ResponseEntity<QuestionResponseDto> createBoard (@RequestBody QuestionRequestDto questionRequestDto,
                                                           @AuthenticationPrincipal UserDetailsImpl userDetails){
        return questionService.createBoard(questionRequestDto, userDetails.getUser());
    }

    @PutMapping("/board/update/{id}")
    public ResponseEntity<QuestionResponseDto> updateBoard (@PathVariable Long id, @RequestBody QuestionRequestDto questionRequestDto,
                                            @AuthenticationPrincipal UserDetailsImpl userDetails){
        return questionService.updateBoard (id, questionRequestDto, userDetails.getUser());
    }

    @DeleteMapping("/board/delete/{id}")
    public ResponseEntity<QuestionResponseDto> deleteBoard (@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return questionService.deleteBoard (id, userDetails.getUser());
    }
}
