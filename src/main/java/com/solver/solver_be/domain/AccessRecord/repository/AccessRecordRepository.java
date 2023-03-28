package com.solver.solver_be.domain.AccessRecord.repository;

import com.solver.solver_be.domain.AccessRecord.entity.AccessRecord;
import com.solver.solver_be.domain.user.entity.Guest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccessRecordRepository extends JpaRepository <AccessRecord,Long> {
    Optional<AccessRecord> findLatestAccessRecordByGuest(Guest guest);
}
