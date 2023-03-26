package com.solver.solver_be.domain.visitform.controller;

import com.solver.solver_be.domain.branch.entity.Branch;
import com.solver.solver_be.domain.visitform.dto.VisitFormRequestDto;
import com.solver.solver_be.domain.visitform.service.VisitFormService;
import com.solver.solver_be.global.response.GlobalResponseDto;
import com.solver.solver_be.global.security.webSecurity.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class VisitForm {

    private final VisitFormService visitorService;

    @PostMapping("/visit")
    public ResponseEntity<GlobalResponseDto> createVisitForm(@Valid @RequestBody VisitFormRequestDto visitorRequestDto, Branch branch,
                                                             @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return visitorService.createVisitForm(visitorRequestDto, branch, userDetails.getUser());
    }

    @GetMapping("/visit")
    public ResponseEntity<GlobalResponseDto> getVisitForm(@RequestParam(value = "userId") String userId,
                                                        @AuthenticationPrincipal UserDetailsImpl userDetails){
        return visitorService.getVisitForm(userId, userDetails.getUser());
    }

    @PutMapping("/visit/{id}")
    public ResponseEntity<GlobalResponseDto> updateVisitForm(@PathVariable Long id,
                                                         @RequestBody VisitFormRequestDto visitorRequestDto,
                                                         @AuthenticationPrincipal UserDetailsImpl userDetails){
        return visitorService.updateVisitForm(id, visitorRequestDto, userDetails.getUser());
    }


    @DeleteMapping("/visit/{id}")
    public ResponseEntity<GlobalResponseDto> deleteVisitForm(@PathVariable Long id,
                                                          @AuthenticationPrincipal UserDetailsImpl userDetails){
        return visitorService.deleteVisitForm(id, userDetails.getUser());
    }
}
