# Desafio Santander F1rst - Consulta de CEP ♨️

Este projeto é um microserviço robusto desenvolvido para o desafio técnico do Santander F1rst. A aplicação realiza consultas de CEP integrando múltiplas fontes de dados, com foco em **Alta Performance**, **Segurança** e **Clean Code**.

---

## 🏗️ Desenho da Solução

A arquitetura foi desenhada seguindo o padrão de **Microserviços** e utiliza o conceito de **Event-Driven Architecture** para garantir que operações de I/O (como logs) não bloqueiem a resposta ao usuário.

```mermaid
flowchart TD
    Client[Client / Postman / Swagger] -->|GET /v1/consulta/{cep}| Controller[ConsultaController]
    Controller -->|Validação Bean Validation| Controller
    Controller -->|buscarCep| Service[CepService]
    
    subgraph Integração Externa
        Service -->|FeignClient| ViaCep[ViaCep API]
        Service -->|FeignClient| Agencia[Agencia Mock API]
    end
    
    Service -->|Publica Evento| Event[CepSearchEvent]
    
    subgraph Processamento Assíncrono
        Event -.->|Thread Separada| Listener[CepLogListener]
        Listener -->|Persistência| DB[(PostgreSQL)]
    end
    
    Service -->|Resposta Rápida| Client
```

---

## 🌟 Pontos Fortes e Diferenciais

### 1. Aplicação de SOLID
*   **S (Single Responsibility):** Classes com responsabilidades únicas (Controller para rotas, Service para lógica, Listener para logs).
*   **O (Open/Closed):** Sistema de eventos permite adicionar novos listeners (ex: métricas) sem alterar a lógica de busca.
*   **D (Dependency Inversion):** Uso de interfaces para clientes HTTP (Feign) e abstração de repositórios.

### 2. Arquitetura Orientada a Eventos (Observer Pattern)
O registro de logs em banco de dados é **100% Assíncrono** (`@Async`). Isso significa que a API responde ao usuário imediatamente após obter os dados do CEP, sem esperar o tempo de gravação no banco de dados.

### 3. Segurança Defensiva
*   **Spring Security:** Implementação de cabeçalhos de proteção (HSTS, CSP, Anti-Clickjacking).
*   **CORS:** Configuração preparada para integração com front-end.
*   **Bean Validation:** Validação rigorosa de formato de CEP na entrada da API.
*   **Tratamento Global de Erros:** Respostas padronizadas que evitam o vazamento de informações do sistema (Stack Traces).

### 4. Resiliência
*   **Feign Fallback:** Caso a API de agências falhe, o sistema utiliza um mecanismo de contingência para não interromper o serviço, retornando informações de "Não disponível" de forma elegante.

---

## 🛠️ Tecnologias Utilizadas
*   **Java 17** (LTS)
*   **Spring Boot 3.x**
*   **Spring Data JPA**
*   **Spring Security**
*   **Spring Cloud OpenFeign**
*   **PostgreSQL**
*   **WireMock** (Para simulação da API de Agências)
*   **Docker & Docker Compose** (Diferencial)

---

## 🚀 Como Executar

### Pré-requisitos:
*   Docker e Docker Compose instalados.

### Passo a passo:
1. Clone o repositório.
2. Na raiz do projeto, execute:
```bash
docker-compose up --build
```
3. A aplicação estará disponível em `http://localhost:8080`.

---

## 📖 Documentação (Swagger)

A API está totalmente documentada e pode ser testada via Swagger UI:
🔗 [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

### Principais Endpoints:
*   `GET /v1/consulta/{cep}`: Realiza a consulta completa.
*   `GET /v3/api-docs`: JSON de especificação da API.

---

## 🧪 Testes
A aplicação conta com cobertura de testes automatizados:
```bash
mvn test
```
*   **Testes Unitários:** Validação de lógica de negócio e fallbacks.
*   **Testes de Integração:** Validação de endpoints e segurança.
