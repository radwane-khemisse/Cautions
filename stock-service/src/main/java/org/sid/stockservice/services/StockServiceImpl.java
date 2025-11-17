package org.sid.stockservice.services;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sid.stockservice.client.CompanyClient;
import org.sid.stockservice.dto.PriceUpdateRequest;
import org.sid.stockservice.dto.StockDTO;
import org.sid.stockservice.entities.StockMarket;
import org.sid.stockservice.mapper.StockMapper;
import org.sid.stockservice.repositories.StockRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class StockServiceImpl implements StockService {

    private final StockRepository stockRepository;
    private final CompanyClient companyClient;

    @Override
    public StockDTO addStock(StockDTO dto) {
        StockMarket stock = StockMapper.toEntity(dto);
        StockMarket saved = stockRepository.save(stock);
        updateCompanyPrice(saved);
        return StockMapper.toDto(saved);
    }

    @Override
    public void deleteStock(Long id) {
        if (!stockRepository.existsById(id)) {
            throw new IllegalArgumentException("Stock not found: " + id);
        }
        stockRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StockDTO> findAll() {
        return stockRepository.findAll()
                .stream()
                .map(StockMapper::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public StockDTO findById(Long id) {
        return stockRepository.findById(id)
                .map(StockMapper::toDto)
                .orElseThrow(() -> new IllegalArgumentException("Stock not found: " + id));
    }

    @CircuitBreaker(name = "company-service", fallbackMethod = "updatePriceFallback")
    protected void updateCompanyPrice(StockMarket stock) {
        PriceUpdateRequest request = new PriceUpdateRequest();
        request.setCurrentPrice(stock.getCloseValue());
        companyClient.updatePrice(stock.getCompanyId(), request);
    }

    protected void updatePriceFallback(StockMarket stock, Throwable ex) {
        log.warn("Failed to update company price for companyId={} (stockId={}) : {}", stock.getCompanyId(), stock.getId(), ex.getMessage());
    }
}
