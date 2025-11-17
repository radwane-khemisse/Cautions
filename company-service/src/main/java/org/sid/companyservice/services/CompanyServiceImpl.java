package org.sid.companyservice.services;

import lombok.RequiredArgsConstructor;
import org.sid.companyservice.dto.CompanyDTO;
import org.sid.companyservice.entities.Company;
import org.sid.companyservice.mapper.CompanyMapper;
import org.sid.companyservice.repositories.CompanyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;

    @Override
    public CompanyDTO create(CompanyDTO request) {
        Company company = CompanyMapper.toEntity(request);
        Company saved = companyRepository.save(company);
        return CompanyMapper.toDto(saved);
    }

    @Override
    public void delete(Long id) {
        if (!companyRepository.existsById(id)) {
            throw new IllegalArgumentException("Company not found: " + id);
        }
        companyRepository.deleteById(id);
    }

    @Override
    public CompanyDTO updatePrice(Long id, BigDecimal newPrice) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Company not found: " + id));
        company.setCurrentPrice(newPrice);
        return CompanyMapper.toDto(company);
    }

    @Override
    public CompanyDTO update(Long id, CompanyDTO request) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Company not found: " + id));
        CompanyMapper.updateEntity(company, request);
        return CompanyMapper.toDto(company);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CompanyDTO> findAll() {
        return companyRepository.findAll()
                .stream()
                .map(CompanyMapper::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public CompanyDTO findById(Long id) {
        return companyRepository.findById(id)
                .map(CompanyMapper::toDto)
                .orElseThrow(() -> new IllegalArgumentException("Company not found: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CompanyDTO> findByDomain(String domain) {
        return companyRepository.findByDomainIgnoreCase(domain)
                .stream()
                .map(CompanyMapper::toDto)
                .toList();
    }
}
