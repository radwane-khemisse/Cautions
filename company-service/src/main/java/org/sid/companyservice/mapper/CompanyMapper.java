package org.sid.companyservice.mapper;

import org.sid.companyservice.dto.CompanyDTO;
import org.sid.companyservice.entities.Company;

public class CompanyMapper {
    private CompanyMapper() {
    }

    public static CompanyDTO toDto(Company company) {
        if (company == null) return null;
        return CompanyDTO.builder()
                .id(company.getId())
                .name(company.getName())
                .ipoDate(company.getIpoDate())
                .currentPrice(company.getCurrentPrice())
                .domain(company.getDomain())
                .build();
    }

    public static Company toEntity(CompanyDTO dto) {
        if (dto == null) return null;
        return Company.builder()
                .id(dto.getId())
                .name(dto.getName())
                .ipoDate(dto.getIpoDate())
                .currentPrice(dto.getCurrentPrice())
                .domain(dto.getDomain())
                .build();
    }

    public static void updateEntity(Company company, CompanyDTO dto) {
        if (dto.getName() != null) {
            company.setName(dto.getName());
        }
        if (dto.getIpoDate() != null) {
            company.setIpoDate(dto.getIpoDate());
        }
        if (dto.getCurrentPrice() != null) {
            company.setCurrentPrice(dto.getCurrentPrice());
        }
        if (dto.getDomain() != null) {
            company.setDomain(dto.getDomain());
        }
    }
}
