package org.sid.stockservice.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "stocks")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockMarket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    @Column(precision = 19, scale = 4)
    private BigDecimal openValue;

    @Column(precision = 19, scale = 4)
    private BigDecimal highValue;

    @Column(precision = 19, scale = 4)
    private BigDecimal lowValue;

    @Column(precision = 19, scale = 4)
    private BigDecimal closeValue;

    private Long volume;

    private Long companyId;
}
