package com.solver.solver_be.domain.visitform.service;

import com.solver.solver_be.domain.branch.entity.Branch;
import com.solver.solver_be.domain.user.entity.User;
import com.solver.solver_be.domain.user.repository.UserRepository;
import com.solver.solver_be.domain.visitform.dto.VisitFormRequestDto;
import com.solver.solver_be.domain.visitform.dto.VisitFromResponseDto;
import com.solver.solver_be.domain.visitform.entity.VisitForm;
import com.solver.solver_be.domain.visitform.repository.VisitFormRepository;
import com.solver.solver_be.global.exception.exceptionType.UserException;
import com.solver.solver_be.global.exception.exceptionType.VisitFormException;
import com.solver.solver_be.global.response.GlobalResponseDto;
import com.solver.solver_be.global.response.ResponseCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VisitFormService {

    private final VisitFormRepository visitorRepository;
    private final UserRepository userRepository;

    // 1. 방문신청서 작성
    @Transactional
    public ResponseEntity<GlobalResponseDto> createVisitForm(VisitFormRequestDto visitorRequestDto, User user){

        // 담당자가 존재를 하는지.
        Optional<User> target = userRepository.findByName(visitorRequestDto.getTarget());
        if(target.isEmpty())
        {
            throw new UserException(ResponseCode.ADMIN_NOT_FOUND);
        }

        VisitForm visitorForm = visitorRepository.saveAndFlush(VisitForm.of(visitorRequestDto, user));

        return ResponseEntity.ok(GlobalResponseDto.of(ResponseCode.VISITOR_WIRTE_SUCCESS, VisitFromResponseDto.of(visitorForm,user)));
    }

    // 2. 방문신청서 가져오기
    @Transactional(readOnly = true)
    public ResponseEntity<GlobalResponseDto> getVisitForm(User user){

        List<VisitForm> visiFormUserList = visitorRepository.findByUserId(user.getId());

        if (visiFormUserList.isEmpty()) {
            throw new VisitFormException(ResponseCode.VISITOR_NOT_FOUND);
        }

        List<VisitFromResponseDto> visitorResponseDtoList = new ArrayList<>();
        for (VisitForm visitorForm : visiFormUserList){
            visitorResponseDtoList.add(VisitFromResponseDto.of(visitorForm, user));
        }

        return  ResponseEntity.ok(GlobalResponseDto.of(ResponseCode.VISITOR_GET_SUCCESS, visitorResponseDtoList));
    }

    // 3. 방문신청서 수정
    @Transactional
    public ResponseEntity<GlobalResponseDto> updateVisitForm(Long id, VisitFormRequestDto visitFormRequestDto,User user){

        VisitForm visitorForm = getVisitorById(id);

        if (!visitorForm.getUser().equals(user)){
            throw new VisitFormException(ResponseCode.VISITOR_UPDATE_FAILED);
        }

        visitorForm.update(visitFormRequestDto);

        VisitFromResponseDto visitorResponseDto = VisitFromResponseDto.of(visitorForm, user);
        return ResponseEntity.ok(GlobalResponseDto.of(ResponseCode.VISITOR_UPDATE_SUCCESS, visitorResponseDto));
    }

    // 4. 방문신청서 삭제
    @Transactional
    public ResponseEntity<GlobalResponseDto> deleteVisitForm(Long id, User user){

        VisitForm visitor = getVisitorById(id);

        if (!visitor.getUser().equals(user)){
            throw new VisitFormException(ResponseCode.VISITOR_UPDATE_FAILED);
        }

        visitorRepository.deleteById(id);

        return ResponseEntity.ok(GlobalResponseDto.of(ResponseCode.VISITOR_DELETE_SUCCESS));
    }

    // ======================================= METHOD ======================================== //

    private VisitForm getVisitorById(Long id) {
        return visitorRepository.findById(id).orElseThrow(() -> new VisitFormException(ResponseCode.VISITOR_NOT_FOUND));
    }
}
