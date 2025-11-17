# Test Systèmes Distribués

## Question 1
Créer un Project Maven incluant les micro-services suivants : company-service, stock-service, chat-bot-service, gateway-service et discovery-service.

![Micro-services creation](images/micro_services%20creation.png)

## Question 2 
Établir une architecture technique du projet


## Question 3
Développer et tester les micro-services discovery-service et gateway-service.

Colonnes ajoutées / configuration:
- discovery-service : port 8761, Eureka server, register/fetch disabled.
- gateway-service : port 8888, Eureka client, discovery locator activé, routes vers `lb://company-service` (`/companies/**`) et `lb://stock-service` (`/stocks/**`).

![Gateway instance in discovery](images/gateway_instance-in-discovery.png)




