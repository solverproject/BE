package com.solver.solver_be.domain.AccessRecord.controller;

import com.solver.solver_be.domain.AccessRecord.dto.AccessRecordRequestDto;
import com.solver.solver_be.domain.AccessRecord.service.AccessService;
import com.solver.solver_be.global.response.GlobalResponseDto;
import com.solver.solver_be.global.security.webSecurity.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AccessController {

    private final AccessService accessService;

    @PostMapping("/access-in")
    public ResponseEntity<GlobalResponseDto> AccessIn(@RequestBody AccessRecordRequestDto accessRecordRequestDto,
                                                      @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return accessService.AccessIn(userDetails.getGuest(), accessRecordRequestDto);
    }

    @PutMapping("/access-out")
    public ResponseEntity<GlobalResponseDto> AccessOut(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return accessService.AccessOut(userDetails.getGuest());
    }
}
