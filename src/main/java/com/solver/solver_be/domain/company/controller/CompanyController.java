package com.solver.solver_be.domain.company.controller;

import com.solver.solver_be.domain.company.dto.CompanyRequestDto;
import com.solver.solver_be.domain.company.service.CompanyService;
import com.solver.solver_be.global.response.GlobalResponseDto;
import com.solver.solver_be.global.security.webSecurity.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor

public class CompanyController {

    private final CompanyService companyService;


    @PostMapping("/company")
    public ResponseEntity<GlobalResponseDto> createCompany(@RequestBody CompanyRequestDto companyRequestDto
    ) {
        return companyService.createCompany(companyRequestDto);
    }

    @GetMapping("/company")
    public ResponseEntity<GlobalResponseDto> getCompanies(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return companyService.getCompanies(userDetails.getGuest());
    }

    @PutMapping("/company/{id}")
    public ResponseEntity<GlobalResponseDto> updateCompany(@PathVariable Long id, @RequestBody CompanyRequestDto companyRequestDto,
                                                           @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return companyService.updateCompany(id, companyRequestDto, userDetails.getAdmin());
    }

    @DeleteMapping("/company/{id}")
    public ResponseEntity<GlobalResponseDto> deleteCompany(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return companyService.deleteCompany(id, userDetails.getAdmin());
    }

}
