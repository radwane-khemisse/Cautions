package org.sid.stockservice.repositories;

import org.sid.stockservice.entities.StockMarket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StockRepository extends JpaRepository<StockMarket, Long> {
    List<StockMarket> findByCompanyIdOrderByDateDesc(Long companyId);
    Optional<StockMarket> findTopByCompanyIdOrderByDateDesc(Long companyId);
}
