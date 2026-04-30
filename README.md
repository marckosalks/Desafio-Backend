# DESAFIO SANTANDER F1RST ♨️

Microserviço de consulta de CEP em Java com Spring Boot, seguindo princípios SOLID.

## Tecnologias usadas:

- API ViaCep
- Spring Boot 4.x
- Spring Cloud OpenFeign
- Docker (banco e API)
- PostgreSQL
- WireMock (mocking)
- AWS

## Arquitetura

```mermaid
flowchart TD
    Client[Client / Postman] -->|GET /consulta/{cep}| Controller[ConsultaController]
    Controller -->|buscarCep| Service[CepService]
    Service -->|GET /ws/{cep}/json/| ViaCep[ViaCep API]
    Service -->|GET /agencia?cep={cep}| Agencia[Agencia API]
    Service -->|publishEvent| Event[CepSearchEvent]
    Event -->|handle| Listener[CepLogListener]
    Listener -->|save| DB[(PostgreSQL)]

    ViaCep -.->|@FeignClient| FeignConfig[Feign Config]
    Agencia -.->|@FeignClient| FeignConfig

    subgraph Async Logging
        Event -->|Observer Pattern| Listener
    end
```

## Executando

```bash
docker-compose up --build
```

A API estará disponível em `http://localhost:8080`.

### Endpoints

- `GET /consulta/{cep}` - Consulta um CEP
- `GET /logs` - Retorna todos os logs de consulta

## Testes

```bash
./mvnw test
```
