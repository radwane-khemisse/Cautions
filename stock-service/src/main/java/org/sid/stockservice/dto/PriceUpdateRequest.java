package org.sid.stockservice.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PriceUpdateRequest {
    private BigDecimal currentPrice;
}
