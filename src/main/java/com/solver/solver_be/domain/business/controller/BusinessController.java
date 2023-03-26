package com.solver.solver_be.domain.business.controller;

import com.solver.solver_be.domain.business.service.BusinessService;
import com.solver.solver_be.global.response.GlobalResponseDto;
import com.solver.solver_be.global.security.webSecurity.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor

public class BusinessController {

    private final BusinessService businessService;

    @GetMapping("business")
    public ResponseEntity<GlobalResponseDto> businessMap(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return businessService.businessMap(userDetails.getUser());
    }
}
