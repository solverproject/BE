package com.solver.solver_be.domain.company.service;

import com.solver.solver_be.domain.company.dto.CompanyRequestDto;
import com.solver.solver_be.domain.company.dto.CompanyResponseDto;
import com.solver.solver_be.domain.company.entity.Company;
import com.solver.solver_be.domain.company.repository.CompanyRepository;
import com.solver.solver_be.domain.user.entity.User;
import com.solver.solver_be.global.exception.exceptionType.CompanyException;
import com.solver.solver_be.global.exception.exceptionType.UserException;
import com.solver.solver_be.global.response.GlobalResponseDto;
import com.solver.solver_be.global.response.ResponseCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;

    // 1. 회사 등록
    @Transactional
    public ResponseEntity<GlobalResponseDto> createCompany(CompanyRequestDto companyRequestDto, User user) {

        // 원래 등록된 회사 였는지.
        if (companyRepository.findByBusinessName(companyRequestDto.getBusinessName()).isPresent()) {
            throw new UserException(ResponseCode.COMPANY_ALREADY_EXIST);
        }

        Company company = companyRepository.saveAndFlush(Company.of(companyRequestDto));

        return ResponseEntity.ok(GlobalResponseDto.of(ResponseCode.COMPANY_REGISTER_SUCCESS, CompanyResponseDto.of(company)));

    }

    // 2. 회사 목록 가져오기
    @Transactional(readOnly = true)
    public ResponseEntity<GlobalResponseDto> getCompanies(User user) {

        List<Company> companyList = companyRepository.findAllByOrderByCreatedAtDesc();

        if (companyList.isEmpty()) {
            throw new CompanyException(ResponseCode.COMPANY_NOT_FOUND);
        }

        List<CompanyResponseDto> companyResponseDtoList = new ArrayList<>();
        for (Company company : companyList){
            companyResponseDtoList.add(CompanyResponseDto.of(company));
        }

        return  ResponseEntity.ok(GlobalResponseDto.of(ResponseCode.COMPANY_GET_SUCCESS, companyResponseDtoList));
    }
}
