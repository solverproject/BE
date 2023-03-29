package com.solver.solver_be.domain.AccessRecord.service;

import com.solver.solver_be.domain.AccessRecord.entity.AccessRecord;
import com.solver.solver_be.domain.AccessRecord.repository.AccessRecordRepository;
import com.solver.solver_be.domain.user.entity.Guest;
import com.solver.solver_be.domain.visitform.entity.VisitForm;
import com.solver.solver_be.domain.visitform.repository.VisitFormRepository;
import com.solver.solver_be.global.response.GlobalResponseDto;
import com.solver.solver_be.global.response.ResponseCode;
import com.solver.solver_be.global.util.TimeStamped;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AccessService extends TimeStamped {
    private final AccessRecordRepository accessRepository;
    private final VisitFormRepository visitFormRepository;

    // 출입 기록 생성
    @Transactional
    public ResponseEntity<GlobalResponseDto> AccessIn(Guest guest, String startDate) {
        VisitForm visitForm = visitFormRepository.findByGuestIdAndStartDate(guest.getId(),startDate);
        LocalDateTime inTime = LocalDateTime.now(); // 현재 시간을 출입 시간으로 설정
        accessRepository.save(AccessRecord.of(inTime,null,guest,visitForm)); // 출입 기록 저장
        return ResponseEntity.ok(GlobalResponseDto.of(ResponseCode.ACCESS_IN_SUCCESS));
    }


    // 출입 기록 업데이트
    @Transactional
    public ResponseEntity<GlobalResponseDto> AccessOut(Guest guest) {
        LocalDateTime outTime = LocalDateTime.now(); // 현재 시간을 나가는 시간으로 설정
        AccessRecord accessRecord = accessRepository.findLatestAccessRecordByGuest(guest)
                .orElseThrow(() -> new IllegalArgumentException("해당 손님의 출입 기록이 없습니다.")); // 가장 최근의 출입 기록을 찾음
        accessRecord.setOutTime(outTime); // 출입 기록에 나가는 시간 업데이트
        accessRepository.save(accessRecord); // 출입 기록 저장
        return ResponseEntity.ok(GlobalResponseDto.of(ResponseCode.ACCESS_OUT_SUCCESS));
    }
}
