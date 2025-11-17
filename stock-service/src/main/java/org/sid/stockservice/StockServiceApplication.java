package org.sid.stockservice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Random;
import java.math.RoundingMode;

@SpringBootApplication
@EnableFeignClients
public class StockServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(StockServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner seedData(org.sid.stockservice.repositories.StockRepository repository) {
        return args -> {
            if (repository.count() > 0) return;
            Random random = new Random();
            for (int i = 1; i <= 5; i++) {
                repository.save(org.sid.stockservice.entities.StockMarket.builder()
                        .date(LocalDate.now().minusDays(random.nextInt(100)))
                        .openValue(randomBig(random))
                        .highValue(randomBig(random))
                        .lowValue(randomBig(random))
                        .closeValue(randomBig(random))
                        .volume(1_000L + random.nextInt(10_000))
                        .companyId((long) i)
                        .build());
            }
        };
    }

    private BigDecimal randomBig(Random random) {
        return BigDecimal.valueOf(50 + random.nextDouble() * 200).setScale(2, RoundingMode.HALF_UP);
    }
}
