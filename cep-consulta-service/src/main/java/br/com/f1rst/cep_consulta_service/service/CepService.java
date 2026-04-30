package br.com.f1rst.cep_consulta_service.service;

import br.com.f1rst.cep_consulta_service.client.ViaCepClient;
import br.com.f1rst.cep_consulta_service.dto.CepDto;
import org.springframework.stereotype.Service;

@Service
public class CepService {

    private final ViaCepClient viaCepClient;

    public CepService(ViaCepClient viaCepClient) {
        this.viaCepClient = viaCepClient;
    }

    public String buscarCep(CepDto cep) {
        return viaCepClient.buscarCep(cep.getCep());
    }

    public String guardaLogs(String data) {
        return data;
    }
}


