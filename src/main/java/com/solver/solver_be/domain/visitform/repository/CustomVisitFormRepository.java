package com.solver.solver_be.domain.visitform.repository;

import com.solver.solver_be.domain.visitform.entity.VisitForm;

import java.util.List;

public interface CustomVisitFormRepository {
    List<VisitForm> findByGuestNameAndLocationAndTargetAndStartDateAndEndDateAndPurposeAndStatus(String guestName, String location, String target, String startDate, String endDate, String purpose, String status);
}
