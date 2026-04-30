package br.com.f1rst.cep_consulta_service.service;

import br.com.f1rst.cep_consulta_service.client.AgenciaClient;
import br.com.f1rst.cep_consulta_service.client.ViaCepClient;
import br.com.f1rst.cep_consulta_service.dto.*;
import br.com.f1rst.cep_consulta_service.entity.CepLog;
import br.com.f1rst.cep_consulta_service.exception.CepNotFoundException;
import br.com.f1rst.cep_consulta_service.repository.CepLogRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CepService {

    private final ViaCepClient viaCepClient;
    private final AgenciaClient agenciaClient;
    private final CepLogRepository repository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public CepService(ViaCepClient viaCepClient,
                      AgenciaClient agenciaClient,
                      CepLogRepository repository) {
        this.viaCepClient = viaCepClient;
        this.agenciaClient = agenciaClient;
        this.repository = repository;
    }

    public ConsultaResponse buscarCep(CepDto cepDto) throws CepNotFoundException {

        ViaCepResponse response = viaCepClient.buscarCep(cepDto.getCep());

        if (response.getErro() != null && response.getErro()) {
            throw new CepNotFoundException("Cep Não encontrado!");
        }


        AgenciaResponse agencia;

        try {
            agencia = agenciaClient.buscarAgencia(cepDto.getCep());

        } catch (Exception e) {

            agencia = AgenciaResponse.builder()
                    .nome("Não disponível")
                    .endereco("Não disponível")
                    .distancia("Não disponível")
                    .build();
        }


        salvarLog(cepDto.getCep(), response, agencia);

        return ConsultaResponse.builder()
                .cep(response)
                .agencia(agencia)
                .build();
    }

    private void salvarLog(String cep, ViaCepResponse response, AgenciaResponse agencia) {
        try {
            String json = objectMapper.writeValueAsString(
                    ConsultaResponse.builder()
                            .cep(response)
                            .agencia(agencia)
                            .build()
            );

            CepLog log = CepLog.builder()
                    .cep(cep)
                    .response(json)
                    .dataConsulta(LocalDateTime.now())
                    .build();

            repository.save(log);

        } catch (Exception e) {
            System.out.println("Erro ao salvar log: " + e.getMessage());
        }
    }
}