package org.sid.stockservice.client;

import org.sid.stockservice.dto.PriceUpdateRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "company-service")
public interface CompanyClient {

    @PutMapping("/companies/{id}/price")
    void updatePrice(@PathVariable("id") Long id, @RequestBody PriceUpdateRequest request);
}
