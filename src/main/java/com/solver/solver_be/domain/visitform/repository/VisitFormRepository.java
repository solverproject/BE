package com.solver.solver_be.domain.visitform.repository;

import com.solver.solver_be.domain.visitform.entity.VisitForm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VisitFormRepository extends JpaRepository<VisitForm, Long> {

    List<VisitForm> findByGuestId(Long userId);

    List<VisitForm> findByTarget(String name);

    VisitForm findByGuestIdAndStartDate(Long id, String startDate);

    VisitForm findByIdAndTarget(Long id, String target);
}
