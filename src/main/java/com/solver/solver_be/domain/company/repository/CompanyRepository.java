package com.solver.solver_be.domain.company.repository;

import com.solver.solver_be.domain.company.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    Optional<Company> findByCompanyName(String companyName);

    List<Company> findAllByOrderByCreatedAtDesc();

    Company findByCompanyToken(String companyToken);

    Optional<Company> findByCompanyTokenAndCompanyName(String companyToken, String companyName);

}
