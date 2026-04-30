package br.com.f1rst.cep_consulta_service.client;

import br.com.f1rst.cep_consulta_service.dto.AgenciaResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class AgenciaClientImpl implements AgenciaClient {

    private final RestTemplate restTemplate = new RestTemplate();


    @Override
    public AgenciaResponse buscarAgencia(String cep) {
        String url = "http://localhost:3001/agencia?cep=" + cep;

        return restTemplate.getForObject(url, AgenciaResponse.class);
    }
}
