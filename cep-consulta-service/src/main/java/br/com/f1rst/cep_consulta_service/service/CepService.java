package br.com.f1rst.cep_consulta_service.service;

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
    private final CepLogRepository repository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public CepService(ViaCepClient viaCepClient,
            CepLogRepository repository) {
        this.viaCepClient = viaCepClient;
        this.repository = repository;
    }

    public CepResponseDto buscarCep(CepDto cepDto) throws CepNotFoundException {

        ViaCepResponse response = viaCepClient.buscarCep(cepDto.getCep());

        if (response.getErro() != null && response.getErro()) {
            throw new CepNotFoundException("Cep Não encontrado!");
        }

        // 🔥 salvar log
        salvarLog(cepDto.getCep(), response);

        return CepResponseDto.builder()
                .cep(response.getCep())
                .logradouro(response.getLogradouro())
                .bairro(response.getBairro())
                .cidade(response.getLocalidade())
                .uf(response.getUf())
                .build();
    }

    private void salvarLog(String cep, ViaCepResponse response) {
        try {
            String json = objectMapper.writeValueAsString(response);

            CepLog log = CepLog.builder()
                    .cep(cep)
                    .response(json)
                    .dataConsulta(LocalDateTime.now())
                    .build();

            repository.save(log);

        } catch (Exception e) {
            // evita quebrar a aplicação por causa de log
            System.out.println("Erro ao salvar log: " + e.getMessage());
        }
    }
}