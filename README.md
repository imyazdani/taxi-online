# Taxi-Online
Taxi-Online is an application to connect passengers to taxi drivers online. 
A `Spring Boot` project with `MySQL` database and `Spring Security` (Basic Authentication).
## How it works
1. Passenger and driver must register their information.
2. Passenger submit a request with a simple gps location on app.
3. Driver get a list of possible trips based on its location
4. Driver can change state of trip

> ***NOTE***
> 
> There is OpenAPI v3 for using these steps and others RESTful APIs. Document REST endpoints -> Swagger at http://localhost:8080/swagger-ui

## Build & Run
Project is dockerized and can use this commands to build and run the app.

To build project
```sh
mvn clean package -DskipTests
```

To run project
```sh
docker compose up -d
```

# Requirements
- Java 11
- Spring Boot 2.7
- Spring Security
- MySQL
- Maven
- Docker and Docker Compose
