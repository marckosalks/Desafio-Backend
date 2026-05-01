package br.com.f1rst.cep_consulta_service.service;

import br.com.f1rst.cep_consulta_service.client.AgenciaClient;
import br.com.f1rst.cep_consulta_service.client.ViaCepClient;
import br.com.f1rst.cep_consulta_service.dto.AgenciaResponse;
import br.com.f1rst.cep_consulta_service.dto.ConsultaResponse;
import br.com.f1rst.cep_consulta_service.dto.CepDto;
import br.com.f1rst.cep_consulta_service.dto.ViaCepResponse;
import br.com.f1rst.cep_consulta_service.event.CepSearchEvent;
import br.com.f1rst.cep_consulta_service.exception.CepNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class CepService {

    private final ViaCepClient viaCepClient;
    private final AgenciaClient agenciaClient;
    private final ApplicationEventPublisher eventPublisher;
    private final ObjectMapper objectMapper;

    public CepService(ViaCepClient viaCepClient,
            AgenciaClient agenciaClient,
            ApplicationEventPublisher eventPublisher,
            ObjectMapper objectMapper) {
        this.viaCepClient = viaCepClient;
        this.agenciaClient = agenciaClient;
        this.eventPublisher = eventPublisher;
        this.objectMapper = objectMapper;
    }

    public ConsultaResponse buscarCep(CepDto cepDto) throws CepNotFoundException {

        ViaCepResponse responseViaCep = viaCepClient.buscarCep(cepDto.getCep());

        if ("true".equals(responseViaCep.getErro())) {
            throw new CepNotFoundException("Cep Não encontrado!");
        }

        AgenciaResponse responseAgencia;

        try {
            responseAgencia = agenciaClient.buscarAgencia(cepDto.getCep());

        } catch (Exception e) {

            responseAgencia = AgenciaResponse.builder()
                    .nome("Não disponível")
                    .endereco("Não disponível")
                    .distancia("Não disponível")
                    .build();
        }

        publishCepSearchEvent(cepDto.getCep(), responseViaCep, responseAgencia);

        return ConsultaResponse.builder()
                .cep(responseViaCep)
                .agencia(responseAgencia)
                .build();
    }

    private void publishCepSearchEvent(String cep, ViaCepResponse response, AgenciaResponse agencia) {
        try {
            String json = objectMapper.writeValueAsString(
                    ConsultaResponse.builder()
                            .cep(response)
                            .agencia(agencia)
                            .build());

            eventPublisher.publishEvent(CepSearchEvent.builder()
                    .cep(cep)
                    .responseJson(json)
                    .build());

        } catch (Exception e) {
            System.out.println("Erro ao publicar evento de busca: " + e.getMessage());
        }
    }
}
