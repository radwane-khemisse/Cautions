package org.sid.companyservice.repositories;

import org.sid.companyservice.entities.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    List<Company> findByDomainIgnoreCase(String domain);
}
