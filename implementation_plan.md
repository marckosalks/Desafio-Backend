# Plano de Implementação - Testes e Documentação

O objetivo é adicionar cobertura de testes unitários para a lógica de negócio e controladores, além de documentar o processo no README.

## Mudanças Propostas

### Testes de Serviço

#### [NEW] [CepServiceTest.java](file:///c:/Users/Marco/OneDrive/Documents/Banco/f1rst/Desafio-F1rts/src/test/java/br/com/f1rst/cep_consulta_service/service/CepServiceTest.java)
- Criar testes unitários para o `CepService` utilizando JUnit 5 e Mockito.
- **Cenários:**
    - Sucesso na busca de CEP.
    - Falha quando o ViaCEP retorna erro (CEP não encontrado).
    - Fallback quando a API de Agência falha (deve retornar "Não disponível").

### Testes de Controlador

#### [NEW] [ConsultaControllerTest.java](file:///c:/Users/Marco/OneDrive/Documents/Banco/f1rst/Desafio-F1rts/src/test/java/br/com/f1rst/cep_consulta_service/controller/ConsultaControllerTest.java)
- Criar testes de integração de fatiamento (slice tests) usando `@WebMvcTest`.
- Validar se o endpoint `/v1/consulta/{cep}` responde corretamente.

### Documentação

#### [MODIFY] [README.md](file:///c:/Users/Marco/OneDrive/Documents/Banco/f1rst/Desafio-F1rts/README.md)
- Expandir a seção de testes com detalhes sobre como rodar e o que está sendo testado.

## Plano de Verificação

### Testes Automatizados
- Executar `./mvnw test` e verificar se todos os testes passam.
