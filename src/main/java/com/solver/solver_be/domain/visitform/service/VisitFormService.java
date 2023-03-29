package com.solver.solver_be.domain.visitform.service;

import com.solver.solver_be.domain.user.entity.Admin;
import com.solver.solver_be.domain.user.entity.Guest;
import com.solver.solver_be.domain.user.repository.AdminRepository;
import com.solver.solver_be.domain.user.repository.GuestRepository;
import com.solver.solver_be.domain.visitform.dto.VisitFormRequestDto;
import com.solver.solver_be.domain.visitform.dto.VisitFormStatusResponseDto;
import com.solver.solver_be.domain.visitform.dto.VisitFromResponseDto;
import com.solver.solver_be.domain.visitform.entity.VisitForm;
import com.solver.solver_be.domain.visitform.repository.VisitFormRepository;
import com.solver.solver_be.global.exception.exceptionType.UserException;
import com.solver.solver_be.global.exception.exceptionType.VisitFormException;
import com.solver.solver_be.global.response.GlobalResponseDto;
import com.solver.solver_be.global.response.ResponseCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class VisitFormService {

    private final VisitFormRepository visitFormRepository;
    private final AdminRepository adminRepository;
    // 1. 방문신청서 작성
    @Transactional
    public ResponseEntity<GlobalResponseDto> createVisitForm(VisitFormRequestDto visitFormRequestDto,  Guest guest) {

        // 담당자가 존재를 하는지.
        Optional<Admin> target = adminRepository.findByName(visitFormRequestDto.getTarget());
        if (target.isEmpty()) {
            throw new UserException(ResponseCode.ADMIN_NOT_FOUND);
        }

        VisitForm visitorForm = visitFormRepository.saveAndFlush(VisitForm.of(visitFormRequestDto, guest));

        return ResponseEntity.ok(GlobalResponseDto.of(ResponseCode.VISITOR_WIRTE_SUCCESS, VisitFromResponseDto.of(visitorForm, guest)));
    }

    // 2. 방문신청서 가져오기
    @Transactional(readOnly = true)
    public ResponseEntity<GlobalResponseDto> getGuestVisitForm(Guest guest) {

        List<VisitForm> visiFormUserList = visitFormRepository.findByGuestId(guest.getId());

        List<VisitFromResponseDto> visitFromResponseDtoList = new ArrayList<>();
        for (VisitForm visitorForm : visiFormUserList) {
            visitFromResponseDtoList.add(VisitFromResponseDto.of(visitorForm));
        }

        return ResponseEntity.ok(GlobalResponseDto.of(ResponseCode.VISITOR_GET_SUCCESS, visitFromResponseDtoList));
    }

    @Transactional(readOnly = true)
    public ResponseEntity<GlobalResponseDto> getAdminVisitForm(Admin admin) {

        List<VisitForm> visiFormUserList = visitFormRepository.findByTarget(admin.getName());

//        if (visiFormUserList.isEmpty()) {
//            throw new VisitFormException(ResponseCode.VISITOR_NOT_FOUND);
//        }

        List<VisitFromResponseDto> visitFromResponseDtoList = new ArrayList<>();
        for (VisitForm visitorForm : visiFormUserList) {
            visitFromResponseDtoList.add(VisitFromResponseDto.of(visitorForm));
        }

        return ResponseEntity.ok(GlobalResponseDto.of(ResponseCode.VISITOR_GET_SUCCESS, visitFromResponseDtoList));
    }

    // 3. 방문신청서 수정 ( Guest )
    @Transactional
    public ResponseEntity<GlobalResponseDto> updateGuestVisitForm(Long id, VisitFormRequestDto visitFormRequestDto, Guest guest) {

        VisitForm visitorForm = getVisitFormById(id);

        if (!visitorForm.getGuest().equals(guest)) {
            throw new VisitFormException(ResponseCode.VISITOR_UPDATE_FAILED);
        }

        visitorForm.update(visitFormRequestDto);

        VisitFromResponseDto visitFromResponseDto = VisitFromResponseDto.of(visitorForm, guest);
        return ResponseEntity.ok(GlobalResponseDto.of(ResponseCode.VISITOR_UPDATE_SUCCESS, visitFromResponseDto));
    }

    // 4. 방문신청서 수정 ( Admin )
    @Transactional
    public ResponseEntity<GlobalResponseDto> updateAdminVisitForm(Long id, VisitFormRequestDto visitFormRequestDto, Admin admin){

        VisitForm visitForm = getVisitFormById(id);

        if(!visitForm.getTarget().equals(admin)){
            throw new VisitFormException(ResponseCode.VISITOR_UPDATE_FAILED);
        }

        visitForm.updateStatus(visitFormRequestDto);

        VisitFormStatusResponseDto visitFormStatusResponseDto = VisitFormStatusResponseDto.of(visitForm);
        return ResponseEntity.ok(GlobalResponseDto.of(ResponseCode.VISITOR_STATUS_UPDATE_SUCCESS, visitFormStatusResponseDto));
    }

    // 5. 방문신청서 삭제
    @Transactional
    public ResponseEntity<GlobalResponseDto> deleteVisitForm(Long id, Guest guest) {

        VisitForm visitForm = getVisitFormById(id);

        if (!visitForm.getGuest().equals(guest)) {
            throw new VisitFormException(ResponseCode.VISITOR_UPDATE_FAILED);
        }

        visitFormRepository.deleteById(id);

        return ResponseEntity.ok(GlobalResponseDto.of(ResponseCode.VISITOR_DELETE_SUCCESS));
    }

    // ======================================= METHOD ======================================== //

    private VisitForm getVisitFormById(Long id) {
        return visitFormRepository.findById(id).orElseThrow(() -> new VisitFormException(ResponseCode.VISITOR_NOT_FOUND));
    }

//    @Scheduled(fixedDelay = 1000)
//    public void scheduleFixedDelay()throws InterruptedException {
//        log.info("Log Test");
//        Thread.sleep(1000L);
//    }
}
