package br.com.f1rst.cep_consulta_service.client;

import br.com.f1rst.cep_consulta_service.dto.AgenciaResponse;

public interface AgenciaClient {
    AgenciaResponse buscarAgencia(String cep);
}