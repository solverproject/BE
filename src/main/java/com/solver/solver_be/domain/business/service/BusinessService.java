package com.solver.solver_be.domain.business.service;

import com.solver.solver_be.domain.business.entity.Business;
import com.solver.solver_be.domain.business.repository.BussinessRepository;
import com.solver.solver_be.domain.user.entity.User;
import com.solver.solver_be.global.response.GlobalResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
@RequiredArgsConstructor
public class BusinessService {

    private final BussinessRepository bussinessRepository;

    @Transactional(readOnly = true)
    public ResponseEntity<GlobalResponseDto> businessMap(User user){

        List<Business> businessList = bussinessRepository.findAll();

        for(Business business: businessList){

        }

    }
}
