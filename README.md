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

Client (Postman)
↓
ConsultaController
↓
CepService
├──→ ViaCep API
├──→ Agencia API
└──→ Publica Evento (CepSearchEvent)
↓
CepLogListener (Async)
↓
PostgreSQL

## Executando

```bash
docker-compose up --build
```

A API estará disponível em `http://localhost:8080`.

### Endpoints

- `GET /consulta/{cep}` - Consulta um CEP
- `GET /logs` - Retorna todos os logs de consulta

## Testes

A aplicação possui cobertura de testes unitários e de integração para garantir a qualidade das funcionalidades principais.

### Tipos de Testes:

- **Unitários (`CepServiceTest`)**: Valida a lógica de negócio, incluindo o tratamento de erro do ViaCep e o fallback (contingência) quando a API de agências está indisponível.
- **Integração (`ConsultaControllerTest`)**: Valida os endpoints da API, contratos JSON e tratamento de exceções usando `MockMvc`.

### Como rodar os testes:

Para executar todos os testes da aplicação, use o comando:

```bash
mvn test
```

Os resultados serão exibidos no console, detalhando o sucesso ou falha de cada cenário testado.
