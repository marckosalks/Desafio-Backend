package br.com.f1rst.cep_consulta_service.client;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ViaCepClientImpl implements ViaCepClient {

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public String buscarCep(String cep) {
        String url = "https://viacep.com.br/ws/" + cep + "/json/";
        return restTemplate.getForObject(url, String.class);
    }
}
