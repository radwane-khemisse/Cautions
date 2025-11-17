package org.sid.stockservice.services;

import org.sid.stockservice.dto.StockDTO;

import java.util.List;

public interface StockService {
    StockDTO addStock(StockDTO dto);
    void deleteStock(Long id);
    List<StockDTO> findAll();
    StockDTO findById(Long id);
}
