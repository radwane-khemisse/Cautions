package org.sid.companyservice.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PriceUpdateRequest {
    private BigDecimal currentPrice;
}
