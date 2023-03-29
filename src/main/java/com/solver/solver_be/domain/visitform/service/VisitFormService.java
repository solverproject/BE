package com.solver.solver_be.domain.visitform.service;

import com.solver.solver_be.domain.user.entity.Admin;
import com.solver.solver_be.domain.user.entity.Guest;
import com.solver.solver_be.domain.user.repository.AdminRepository;
import com.solver.solver_be.domain.visitform.dto.AccessStatusResponseDto;
import com.solver.solver_be.domain.visitform.dto.VisitFormRequestDto;
import com.solver.solver_be.domain.visitform.dto.VisitFormResponseDto;
import com.solver.solver_be.domain.visitform.entity.VisitForm;
import com.solver.solver_be.domain.visitform.repository.VisitFormRepository;
import com.solver.solver_be.global.exception.exceptionType.UserException;
import com.solver.solver_be.global.exception.exceptionType.VisitFormException;
import com.solver.solver_be.global.response.GlobalResponseDto;
import com.solver.solver_be.global.response.ResponseCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class VisitFormService {
    private final VisitFormRepository visitFormRepository;
    private final AdminRepository adminRepository;

    // 1. 방문신청서 작성
    @Transactional
    public ResponseEntity<GlobalResponseDto> createVisitForm(VisitFormRequestDto visitFormRequestDto, Guest guest) {

        // 담당자가 존재를 하는지.
        Optional<Admin> target = adminRepository.findByName(visitFormRequestDto.getTarget());
        if (target.isEmpty()) {
            throw new UserException(ResponseCode.ADMIN_NOT_FOUND);
        }

        VisitForm visitForm = visitFormRepository.saveAndFlush(VisitForm.of(visitFormRequestDto, guest));

        return ResponseEntity.ok(GlobalResponseDto.of(ResponseCode.VISITOR_WIRTE_SUCCESS, VisitFormResponseDto.of(visitForm, guest)));
    }

    // 2. 방문신청서 가져오기
    @Transactional(readOnly = true)
    public ResponseEntity<GlobalResponseDto> getGuestVisitForm(Guest guest) {

        List<VisitForm> visiFormUserList = visitFormRepository.findByGuestId(guest.getId());

        List<VisitFormResponseDto> visitFromResponseDtoList = new ArrayList<>();
        for (VisitForm visitorForm : visiFormUserList) {
            visitFromResponseDtoList.add(VisitFormResponseDto.of(visitorForm));
        }

        return ResponseEntity.ok(GlobalResponseDto.of(ResponseCode.VISITOR_GET_SUCCESS, visitFromResponseDtoList));
    }

    @Transactional(readOnly = true)
    public ResponseEntity<GlobalResponseDto> getAdminVisitForm(Admin admin) {

        List<VisitForm> visitFormUserList = visitFormRepository.findByTarget(admin.getName());

//        if (visitFormUserList.isEmpty()) {
//            throw new VisitFormException(ResponseCode.VISITOR_NOT_FOUND);
//        }

        List<VisitFormResponseDto> visitFormResponseDtos = new ArrayList<>();
        for (VisitForm visitorForm : visitFormUserList) {
            visitFormResponseDtos.add(VisitFormResponseDto.of(visitorForm));
        }

        return ResponseEntity.ok(GlobalResponseDto.of(ResponseCode.VISITOR_GET_SUCCESS, visitFormResponseDtos));
    }

    // 3. 방문신청서 수정 ( Guest )
    @Transactional
    public ResponseEntity<GlobalResponseDto> updateGuestVisitForm(Long id, VisitFormRequestDto visitFormRequestDto, Guest guest) {

        VisitForm visitorForm = getVisitFormById(id);

        if (!visitorForm.getGuest().equals(guest)) {
            throw new VisitFormException(ResponseCode.VISITOR_UPDATE_FAILED);
        }

        visitorForm.update(visitFormRequestDto);

        VisitFormResponseDto visitFormResponseDto = VisitFormResponseDto.of(visitorForm, guest);
        return ResponseEntity.ok(GlobalResponseDto.of(ResponseCode.VISITOR_UPDATE_SUCCESS, visitFormResponseDto));
    }

    // 4. 방문신청서 수정 ( Admin )
    @Transactional
    public ResponseEntity<GlobalResponseDto> updateAdminVisitForm(Long id, VisitFormRequestDto visitFormRequestDto, Admin admin) {

        VisitForm visitForm = visitFormRepository.findByIdAndTarget(id, admin.getName());

        if(!visitForm.getTarget().equals(admin.getName())){

            throw new VisitFormException(ResponseCode.VISITOR_UPDATE_FAILED);
        }

        visitForm.updateStatus(visitFormRequestDto);

//        VisitFormResponseDto visitFromResponseDto = VisitFormResponseDto.of(visitForm);
        return ResponseEntity.ok(GlobalResponseDto.of(ResponseCode.VISITOR_STATUS_UPDATE_SUCCESS));
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

    public ResponseEntity<GlobalResponseDto> getAccessStatus(Admin admin) {

        List<VisitForm> visitFormList = visitFormRepository.findByTarget(admin.getName());
        Map<String, List<VisitForm>> visitFormMap = new HashMap<>();

        // VisitForm 리스트를 날짜별 정리.
        for (VisitForm visitForm : visitFormList) {
            String date = visitForm.getStartDate();
            List<VisitForm> visitFormByDate = visitFormMap.getOrDefault(date, new ArrayList<>());
            visitFormByDate.add(visitForm);
            visitFormMap.put(date, visitFormByDate);
        }

        // 날짜별로 방문 예약 내역을 체크하여 AccessStatusResponseDto 객체를 생성.
        List<AccessStatusResponseDto> accessStatusResponseDtoList = new ArrayList<>();
        for (String date : visitFormMap.keySet()) {
            List<VisitForm> visitFormByDate = visitFormMap.get(date);

            int applyCount = visitFormByDate.size();
            int approveCount = 0;
            for (VisitForm visitForm : visitFormByDate) {
                if ("3".equals(visitForm.getStatus())) {
                    approveCount += 1;
                }
            }
            AccessStatusResponseDto accessStatusResponseDto = AccessStatusResponseDto.of(date, (long) applyCount, (long) approveCount, (long) (applyCount + approveCount));
            accessStatusResponseDtoList.add(accessStatusResponseDto);
        }
        return ResponseEntity.ok(GlobalResponseDto.of(ResponseCode.ACCESS_STATUS_SUCCESS, accessStatusResponseDtoList));
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
