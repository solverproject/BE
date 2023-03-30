package com.solver.solver_be.domain.visitform.repository;

import com.solver.solver_be.domain.visitform.entity.VisitForm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VisitFormRepository extends JpaRepository<VisitForm, Long>, CustomVisitFormRepository {

    List<VisitForm> findByGuestId(Long userId);

    List<VisitForm> findByTarget(String name);

    VisitForm findByGuestIdAndStartDateAndLocation(Long id, String startDate, String location);

    VisitForm findByIdAndTarget(Long id, String target);

    Optional<VisitForm> findAllByStartDateAndLocation(String startDate, String location);
}
