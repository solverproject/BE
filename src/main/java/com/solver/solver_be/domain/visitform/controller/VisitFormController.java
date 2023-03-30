package com.solver.solver_be.domain.visitform.controller;

import com.solver.solver_be.domain.visitform.dto.VisitFormRequestDto;
import com.solver.solver_be.domain.visitform.dto.VisitFormSearchRequestDto;
import com.solver.solver_be.domain.visitform.service.VisitFormService;
import com.solver.solver_be.global.response.GlobalResponseDto;
import com.solver.solver_be.global.security.webSecurity.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class VisitFormController {

    private final VisitFormService visitorService;

    @PostMapping("/visit")
    public ResponseEntity<GlobalResponseDto> createVisitForm(@Valid @RequestBody VisitFormRequestDto visitorRequestDto,
                                                             @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return visitorService.createVisitForm(visitorRequestDto, userDetails.getGuest());
    }

    @GetMapping("/visit/guest")
    public ResponseEntity<GlobalResponseDto> getGuestVisitForm(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return visitorService.getGuestVisitForm(userDetails.getGuest());
    }


    @GetMapping("/visit/admin")
    public ResponseEntity<GlobalResponseDto> getAdminVisitForm(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return visitorService.getAdminVisitForm(userDetails.getAdmin());
    }

    @PutMapping("/visit/guest/{id}")
    public ResponseEntity<GlobalResponseDto> updateGuestVisitForm(@PathVariable Long id,
                                                                  @RequestBody VisitFormRequestDto visitorRequestDto,
                                                                  @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return visitorService.updateGuestVisitForm(id, visitorRequestDto, userDetails.getGuest());
    }

    @PutMapping("/visit/admin/{id}")
    public ResponseEntity<GlobalResponseDto> updateAdminVisitForm(@PathVariable Long id,
                                                                  @RequestBody VisitFormRequestDto visitFormRequestDto,
                                                                  @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return visitorService.updateAdminVisitForm(id, visitFormRequestDto, userDetails.getAdmin());
    }

    @DeleteMapping("/visit/{id}")
    public ResponseEntity<GlobalResponseDto> deleteVisitForm(@PathVariable Long id,
                                                             @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return visitorService.deleteVisitForm(id, userDetails.getGuest());
    }

    @GetMapping("/visit/access-status")
    public ResponseEntity<GlobalResponseDto> getAccessStatus(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return visitorService.getAccessStatus(userDetails.getAdmin());
    }

    @GetMapping("/visit-forms/search")
    public ResponseEntity<GlobalResponseDto> searchVisitForms(@RequestBody VisitFormSearchRequestDto visitFormSearchRequestDto,
                                                        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return visitorService.searchVisitForms(visitFormSearchRequestDto,userDetails.getAdmin());
    }

}
