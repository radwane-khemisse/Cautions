package org.sid.companyservice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;

@SpringBootApplication
public class CompanyServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CompanyServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner seedData(org.sid.companyservice.repositories.CompanyRepository repository) {
        return args -> {
            if (repository.count() > 0) return;

            List<String> domains = List.of("IT", "AI", "Banque", "Assurance", "FinTech");
            Random random = new Random();

            for (int i = 1; i <= 5; i++) {
                String name = "Company-" + i;
                String domain = domains.get(random.nextInt(domains.size()));
                LocalDate ipoDate = LocalDate.now().minusDays(random.nextInt(2000));
                BigDecimal price = BigDecimal.valueOf(50 + random.nextDouble() * 200).setScale(2, BigDecimal.ROUND_HALF_UP);

                repository.save(org.sid.companyservice.entities.Company.builder()
                        .name(name)
                        .domain(domain)
                        .ipoDate(ipoDate)
                        .currentPrice(price)
                        .build());
            }
        };
    }

}
