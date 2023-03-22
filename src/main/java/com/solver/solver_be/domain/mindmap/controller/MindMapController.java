package com.solver.solver_be.domain.mindmap.controller;

import com.solver.solver_be.domain.mindmap.dto.MindMapRequestDto;
import com.solver.solver_be.domain.mindmap.service.MindMapService;
import com.solver.solver_be.global.response.GlobalResponseDto;
import com.solver.solver_be.global.security.webSecurity.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MindMapController {

    private final MindMapService mindMapService;

    @PostMapping("/mindmap")
    public ResponseEntity<GlobalResponseDto> createMindMap(@PathVariable Long id,
                                                           @RequestBody MindMapRequestDto mindMapRequestDto,
                                                           @AuthenticationPrincipal UserDetailsImpl userDetails){
        return mindMapService.createMindMap(id, mindMapRequestDto, userDetails.getUser());
    }

    @GetMapping("/mindmap/{id}")
    public ResponseEntity<GlobalResponseDto> changeMindMap(@PathVariable Long id,
                                                           @AuthenticationPrincipal UserDetailsImpl userDetails){
        return mindMapService.changeMindMap(id, userDetails.getUser());
    }

    @PutMapping("/mindmap/{id}")
    public ResponseEntity<GlobalResponseDto> updateMindMap(@PathVariable Long id,
                                                           @RequestBody MindMapRequestDto mindMapRequestDto,
                                                           @AuthenticationPrincipal UserDetailsImpl userDetails){
        return mindMapService.updateMindMap(id, mindMapRequestDto, userDetails.getUser());
    }

    @DeleteMapping("/mindmap/{id}")
    public ResponseEntity<GlobalResponseDto> deleteMindMap(@PathVariable Long id,
                                                           @AuthenticationPrincipal UserDetailsImpl userDetails){
        return mindMapService.deleteMindMap(id, userDetails.getUser());
    }
}
