package org.sid.stockservice.controllers;

import lombok.RequiredArgsConstructor;
import org.sid.stockservice.dto.StockDTO;
import org.sid.stockservice.services.StockService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stocks")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class StockController {

    private final StockService stockService;

    @PostMapping
    public ResponseEntity<StockDTO> addStock(@RequestBody StockDTO request) {
        StockDTO created = stockService.addStock(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteStock(@PathVariable Long id) {
        stockService.deleteStock(id);
    }

    @GetMapping
    public List<StockDTO> findAll() {
        return stockService.findAll();
    }

    @GetMapping("/{id}")
    public StockDTO findById(@PathVariable Long id) {
        return stockService.findById(id);
    }
}
