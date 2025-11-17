# Test Systemes Distribues

## Question 1
Creer un Project Maven incluant les micro-services suivants : company-service, stock-service, chat-bot-service, gateway-service et discovery-service.

![Micro-services creation](images/micro_services%20creation.png)

## Question 2
Etablir une architecture technique du projet.

## Question 3
Developper et tester les micro-services discovery-service et gateway-service.

Configuration :
- discovery-service : port 8761, Eureka server, register/fetch disabled.
- gateway-service : port 8888, Eureka client, discovery locator active, routes vers `lb://company-service` (`/companies/**`) et `lb://stock-service` (`/stocks/**`).

![Gateway instance in discovery](images/gateway_instance-in-discovery.png)

## Question 4
Developper et tester le micro-service company-service.

Implémentation  :
1) Entite + Repository
```java
@Entity @Table(name = "companies")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Company {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(nullable = false, unique = true)
  private String name;
  private LocalDate ipoDate;
  @Column(precision = 19, scale = 4)
  private BigDecimal currentPrice;
  private String domain;
}
```
2) DTOs + Mapper
```java
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class CompanyDTO { Long id; String name; LocalDate ipoDate; BigDecimal currentPrice; String domain; }

public static CompanyDTO toDto(Company c) { ... }
public static Company toEntity(CompanyDTO dto) { ... }
public static void updateEntity(Company c, CompanyDTO dto) { ... }
```
3) Service
```java
public interface CompanyService { create/delete/updatePrice/findAll/findById/findByDomain; }

@Service
public class CompanyServiceImpl implements CompanyService {
  public CompanyDTO updatePrice(Long id, BigDecimal newPrice) {
    Company company = repo.findById(id).orElseThrow(...);
    company.setCurrentPrice(newPrice);
    return CompanyMapper.toDto(company);
  }
}
```
4) Controller REST `/companies`
```java
@RestController
@RequestMapping("/companies")
public class CompanyController {
  @PostMapping public ResponseEntity<CompanyDTO> create(@RequestBody CompanyDTO request) { ... }
  @DeleteMapping("/{id}") public void delete(@PathVariable Long id) { ... }
  @PutMapping("/{id}/price") public CompanyDTO updatePrice(@PathVariable Long id, @RequestBody PriceUpdateRequest req) { ... }
  @GetMapping public List<CompanyDTO> findAll() { ... }
  @GetMapping("/{id}") public CompanyDTO findById(@PathVariable Long id) { ... }
  @GetMapping("/domain/{domain}") public List<CompanyDTO> findByDomain(@PathVariable String domain) { ... }
}
```
5) Config `application.properties` : port 8081, H2 en memoire, JPA ddl-auto update, Eureka client, config server desactive.
6) Seed : insertion de 5 companies aleatoires au demarrage si base vide.

Tests :
![Liste des companies](images/list_companies.png)
![Company par id](images/company_by_id.png)

## Question 5
Developper et tester le micro-service stock-service.

Implémentation  :
1) Entite + Repository
```java
@Entity @Table(name = "stocks")
@Data @Builder
public class StockMarket {
  Long id; LocalDate date; BigDecimal openValue; BigDecimal highValue; BigDecimal lowValue;
  BigDecimal closeValue; Long volume; Long companyId;
}
```
2) DTO + Mapper
```java
public class StockDTO { ... }
public class StockMapper { toDto/toEntity/updateEntity }
```
3) Service + Feign + Resilience4j
```java
@FeignClient(name = "company-service")
void updatePrice(@PathVariable Long id, @RequestBody PriceUpdateRequest req);

@CircuitBreaker(name = "company-service", fallbackMethod = "updatePriceFallback")
protected void updateCompanyPrice(StockMarket stock) {
  companyClient.updatePrice(stock.getCompanyId(), req);
}
```
4) Controller REST `/stocks`
```java
@RestController
@RequestMapping("/stocks")
public class StockController {
  @PostMapping public ResponseEntity<StockDTO> addStock(@RequestBody StockDTO dto) { ... }
  @DeleteMapping("/{id}") public void deleteStock(@PathVariable Long id) { ... }
  @GetMapping public List<StockDTO> findAll() { ... }
  @GetMapping("/{id}") public StockDTO findById(@PathVariable Long id) { ... }
}
```
5) Config `application.properties` : port 8082, H2 en memoire, JPA ddl-auto update, Eureka client, Feign circuit breaker active, config server desactive.
6) Seed : 5 cotations aleatoires au demarrage si base vide.

Tests :

![Liste des stocks](images/list_stocks.png)
![Stock par id](images/stock_by_id.png)

## Question 7
Developper un frontend web Angular (design simple bleu/blanc):
- Navbar bleue (liens Dashboard / Companies / Stocks), contenu sur fond bleu très clair avec cartes/formulaires blancs.
- Page Companies : liste + détail, création, édition, suppression.
- Page Stocks : liste + détail, ajout, suppression.
- Page Dashboard : aperçu synthétique (cartes/counts) et accès rapide vers Companies/Stocks.
