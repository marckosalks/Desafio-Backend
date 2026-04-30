# Plano de Melhoria: Microserviço de Consulta de CEP

Este plano visa refatorar a aplicação existente para melhor aderência aos princípios SOLID, melhorar a testabilidade com mocks e otimizar a infraestrutura Docker.

## User Review Required

> [!IMPORTANT]
> A refatoração do `CepService` para usar eventos (Observer Pattern) mudará a forma como os logs são salvos, tornando o processo assíncrono e desacoplado. Isso é uma excelente prática de SOLID (SRP).

## Proposta de Mudanças

### 1. Refatoração de Arquitetura e SOLID

#### [MODIFY] [CepService.java](file:///c:/Users/Marco/OneDrive/Documents/Banco/f1rst/Desafio-F1rts/src/main/java/br/com/f1rst/cep_consulta_service/service/CepService.java)
- Remover a lógica direta de salvar no banco de dados.
- Publicar um evento customizado (`CepSearchEvent`) após a consulta bem-sucedida.
- Injetar o `ObjectMapper` gerenciado pelo Spring.

#### [NEW] [CepSearchEvent.java](file:///c:/Users/Marco/OneDrive/Documents/Banco/f1rst/Desafio-F1rts/src/main/java/br/com/f1rst/cep_consulta_service/event/CepSearchEvent.java)
- Classe POJO para carregar os dados do evento de busca.

#### [NEW] [CepLogListener.java](file:///c:/Users/Marco/OneDrive/Documents/Banco/f1rst/Desafio-F1rts/src/main/java/br/com/f1rst/cep_consulta_service/event/listener/CepLogListener.java)
- Componente `@EventListener` que escuta o `CepSearchEvent` e salva no banco de dados. Isso aplica o **Open/Closed Principle** (podemos adicionar novos comportamentos sem mudar o service).

### 2. Comunicação Externa (Refatoração de Clients)

#### [MODIFY] [pom.xml](file:///c:/Users/Marco/OneDrive/Documents/Banco/f1rst/Desafio-F1rts/pom.xml)
- Adicionar dependência do `Spring Cloud OpenFeign` para simplificar as chamadas HTTP.
- Adicionar dependência do `WireMock` para testes.

#### [NEW] [FeignConfig.java](file:///c:/Users/Marco/OneDrive/Documents/Banco/f1rst/Desafio-F1rts/src/main/java/br/com/f1rst/cep_consulta_service/config/FeignConfig.java)
- Configuração para habilitar o Feign.

#### [MODIFY] [ViaCepClient.java](file:///c:/Users/Marco/OneDrive/Documents/Banco/f1rst/Desafio-F1rts/src/main/java/br/com/f1rst/cep_consulta_service/client/ViaCepClient.java)
- Transformar em uma interface `@FeignClient`. Isso remove a necessidade de `RestTemplate` manual e `Impl` classes.

### 3. Docker e Infraestrutura

#### [MODIFY] [Dockerfile](file:///c:/Users/Marco/OneDrive/Documents/Banco/f1rst/Desafio-F1rts/Dockerfile)
- Implementar **Multi-stage build** para compilar o código dentro do container, garantindo que qualquer ambiente consiga rodar sem necessidade de Maven instalado localmente.

#### [MODIFY] [docker-compose.yml](file:///c:/Users/Marco/OneDrive/Documents/Banco/f1rst/Desafio-F1rts/docker-compose.yml)
- Adicionar um serviço para o **WireMock** (opcional, mas recomendado para o diferencial do desafio).

### 4. Documentação e Desenho de Solução

#### [MODIFY] [README.md](file:///c:/Users/Marco/OneDrive/Documents/Banco/f1rst/Desafio-F1rts/README.md)
- Adicionar um diagrama Mermaid explicando o fluxo da aplicação.

## Verificação

### Testes Automatizados
- Executar `./mvnw test` para garantir que a lógica de negócio e eventos funcionam.
- Testar a integração com o banco de dados via Docker.

### Verificação Manual
- Subir o ambiente com `docker-compose up --build`.
- Realizar chamadas via Postman/Curl e verificar se os logs aparecem no banco de dados Postgres.
