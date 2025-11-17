package org.sid.companyservice.services;

import org.sid.companyservice.dto.CompanyDTO;

import java.math.BigDecimal;
import java.util.List;

public interface CompanyService {
    CompanyDTO create(CompanyDTO request);

    void delete(Long id);

    CompanyDTO updatePrice(Long id, BigDecimal newPrice);

    List<CompanyDTO> findAll();

    CompanyDTO findById(Long id);

    List<CompanyDTO> findByDomain(String domain);
}
