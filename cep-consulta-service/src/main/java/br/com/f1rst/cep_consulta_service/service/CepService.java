package br.com.f1rst.cep_consulta_service.service;

import br.com.f1rst.cep_consulta_service.client.ViaCepClient;
import br.com.f1rst.cep_consulta_service.dto.CepDto;
import br.com.f1rst.cep_consulta_service.dto.CepResponseDto;
import br.com.f1rst.cep_consulta_service.dto.ViaCepResponse;
import br.com.f1rst.cep_consulta_service.exception.CepNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CepService {

    private final ViaCepClient viaCepClient;

    public CepService(ViaCepClient viaCepClient) {
        this.viaCepClient = viaCepClient;
    }

    public CepResponseDto buscarCep(CepDto cepDto) throws CepNotFoundException {

        ViaCepResponse response = viaCepClient.buscarCep(cepDto.getCep());

        if(response.getErro() != null && response.getErro()){
            throw new CepNotFoundException("Cep Não encontrado!");
        }

        return CepResponseDto.builder()
                .cep(response.getCep())
                .logradouro(response.getLogradouro())
                .bairro(response.getBairro())
                .cidade(response.getLocalidade())
                .uf(response.getUf())
                .build();
    }

    public String guardaLogs(String data) {
        return data;
    }
}


