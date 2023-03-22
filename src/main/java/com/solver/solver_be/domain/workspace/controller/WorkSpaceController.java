package com.solver.solver_be.domain.workspace.controller;

import com.solver.solver_be.domain.workspace.dto.WorkSpaceRequestDto;
import com.solver.solver_be.domain.workspace.service.WorkSpaceService;
import com.solver.solver_be.global.response.GlobalResponseDto;
import com.solver.solver_be.global.security.webSecurity.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class WorkSpaceController {

    private final WorkSpaceService workSpaceService;

    @PostMapping("/workSpace")
    public ResponseEntity<GlobalResponseDto> createWorkSpace(@RequestBody WorkSpaceRequestDto workSpaceRequestDto,
                                                             @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return workSpaceService.createWorkSpace(workSpaceRequestDto, userDetails.getUser());
    }

    @GetMapping("/workSpace/{id}")
    public ResponseEntity<GlobalResponseDto> changeWorkSpace(@PathVariable Long id,
                                                             @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return workSpaceService.changeWorkSpace(id, userDetails.getUser());
    }
}
