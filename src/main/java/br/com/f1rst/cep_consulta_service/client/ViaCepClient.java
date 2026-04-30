package br.com.f1rst.cep_consulta_service.client;

import br.com.f1rst.cep_consulta_service.dto.ViaCepResponse;

public interface ViaCepClient {
    ViaCepResponse buscarCep(String cep);
}
