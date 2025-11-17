package org.sid.companyservice.controllers;

import lombok.RequiredArgsConstructor;
import org.sid.companyservice.dto.CompanyDTO;
import org.sid.companyservice.dto.PriceUpdateRequest;
import org.sid.companyservice.services.CompanyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    @PostMapping
    public ResponseEntity<CompanyDTO> create(@RequestBody CompanyDTO request) {
        CompanyDTO created = companyService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        companyService.delete(id);
    }

    @PutMapping("/{id}/price")
    public CompanyDTO updatePrice(@PathVariable Long id, @RequestBody PriceUpdateRequest request) {
        return companyService.updatePrice(id, request.getCurrentPrice());
    }

    @GetMapping
    public List<CompanyDTO> findAll() {
        return companyService.findAll();
    }

    @GetMapping("/{id}")
    public CompanyDTO findById(@PathVariable Long id) {
        return companyService.findById(id);
    }

    @GetMapping("/domain/{domain}")
    public List<CompanyDTO> findByDomain(@PathVariable String domain) {
        return companyService.findByDomain(domain);
    }
}
