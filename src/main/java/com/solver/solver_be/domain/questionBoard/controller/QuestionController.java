package com.solver.solver_be.domain.questionBoard.controller;

import com.solver.solver_be.domain.questionBoard.dto.KeywordRequestDto;
import com.solver.solver_be.domain.questionBoard.dto.QuestionRequestDto;
import com.solver.solver_be.domain.questionBoard.service.QuestionService;
import com.solver.solver_be.global.response.GlobalResponseDto;
import com.solver.solver_be.global.security.webSecurity.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    @PostMapping("/boards")
    public ResponseEntity<GlobalResponseDto> createBoard(@RequestPart(value = "data") QuestionRequestDto questionRequestDto,
                                                         @RequestPart(value = "file") List<MultipartFile> multipartFilelist,
                                                         @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {
        return questionService.createBoard(questionRequestDto, multipartFilelist, userDetails.getUser());
    }

    @GetMapping("/boards")
    public ResponseEntity<GlobalResponseDto> getBoards(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return questionService.getBoards(userDetails.getUser());
    }

    @GetMapping("/board/{id}")
    public ResponseEntity<GlobalResponseDto> getBoard(@PathVariable Long id,
                                                      @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return questionService.getBoard(id, userDetails.getUser());
    }

    @PutMapping("/board/{id}")
    public ResponseEntity<GlobalResponseDto> updateBoard(@PathVariable Long id,
                                                         @RequestBody QuestionRequestDto questionRequestDto,
                                                         @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return questionService.updateBoard(id, userDetails.getUser(), questionRequestDto);
    }

    @DeleteMapping("/board/{id}")
    public ResponseEntity<GlobalResponseDto> deleteBoard(@PathVariable Long id,
                                                         @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return questionService.deleteBoard(id, userDetails.getUser());
    }

    @GetMapping("/keyword")
    public ResponseEntity<GlobalResponseDto> getKeyWordBoard(@RequestBody KeywordRequestDto keywordRequestDto,
                                                             @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return questionService.getKeyWordBoard(keywordRequestDto, userDetails.getUser());
    }
}
