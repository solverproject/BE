package com.solver.solver_be.domain.company.controller;

import com.solver.solver_be.domain.company.dto.CompanyRequestDto;
import com.solver.solver_be.domain.company.service.CompanyService;
import com.solver.solver_be.global.response.GlobalResponseDto;
import com.solver.solver_be.global.security.webSecurity.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor

public class CompanyController {

    private final CompanyService companyService;

    @PostMapping("/company")
    public ResponseEntity<GlobalResponseDto> createCompany(@RequestBody CompanyRequestDto companyRequestDto,
                                                           @AuthenticationPrincipal UserDetailsImpl userDetails){
        return companyService.createCompany(companyRequestDto,userDetails.getUser());
    }

    @GetMapping("/company")
    public ResponseEntity<GlobalResponseDto> getCompanies(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return companyService.getCompanies(userDetails.getUser());
    }

}
