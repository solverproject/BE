package com.solver.solver_be.domain.visitform.service;

import com.solver.solver_be.domain.branch.entity.Branch;
import com.solver.solver_be.domain.user.entity.User;
import com.solver.solver_be.domain.visitform.dto.VisitFormRequestDto;
import com.solver.solver_be.domain.visitform.dto.VisitFromResponseDto;
import com.solver.solver_be.domain.visitform.entity.VisitForm;
import com.solver.solver_be.domain.visitform.repository.VisitFormRepository;
import com.solver.solver_be.global.exception.exceptionType.VisitFormException;
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
public class VisitFormService {

    private final VisitFormRepository visitorRepository;

    @Transactional
    public ResponseEntity<GlobalResponseDto> createVisitForm(VisitFormRequestDto visitorRequestDto, Branch branch, User user){

        VisitForm visitor = visitorRepository.saveAndFlush(VisitForm.of(visitorRequestDto, branch, user));

        return ResponseEntity.ok(GlobalResponseDto.of(ResponseCode.VISITOR_WIRTE_SUCCESS, VisitFromResponseDto.of(visitor)));
    }

    @Transactional(readOnly = true)
    public ResponseEntity<GlobalResponseDto> getVisitForm(String userId, User user){

        List<VisitForm> visiFormuserList = visitorRepository.findByUserId(userId);
        if (visiFormuserList.isEmpty()) {
            throw new VisitFormException(ResponseCode.VISITOR_NOT_FOUND);
        }

        List<VisitForm> visitFormList = visitorRepository.findAllByOrderByVisitTimeDesc();
        List<VisitFromResponseDto> visitorResponseDtoList = new ArrayList<>();
        for (VisitForm visitor : visitFormList){
            visitorResponseDtoList.add(VisitFromResponseDto.of(visitor));
        }
        return  ResponseEntity.ok(GlobalResponseDto.of(ResponseCode.VISITOR_GET_SUCCESS, visitorResponseDtoList));
    }

    @Transactional
    public ResponseEntity<GlobalResponseDto> updateVisitForm(Long id, VisitFormRequestDto visitorRequestDto, User user){

        VisitForm visitor = getVisitorById(id);

        if (!visitor.getUser().equals(user)){
            throw new VisitFormException(ResponseCode.VISITOR_UPDATE_FAILED);
        }

        visitor.update(visitorRequestDto);

        VisitFromResponseDto visitorResponseDto = VisitFromResponseDto.of(visitor);

        return ResponseEntity.ok(GlobalResponseDto.of(ResponseCode.VISITOR_UPDATE_SUCCESS, visitorResponseDto));
    }

    @Transactional
    public ResponseEntity<GlobalResponseDto> deleteVisitForm(Long id, User user){

        VisitForm visitor = getVisitorById(id);

        if (!visitor.getUser().equals(user)){
            throw new VisitFormException(ResponseCode.VISITOR_UPDATE_FAILED);
        }

        visitorRepository.deleteById(id);

        return ResponseEntity.ok(GlobalResponseDto.of(ResponseCode.VISITOR_DELETE_SUCCESS));
    }


    private VisitForm getVisitorById(Long id) {
        return visitorRepository.findById(id).orElseThrow(() -> new VisitFormException(ResponseCode.VISITOR_NOT_FOUND));
    }
}
