package br.com.f1rst.cep_consulta_service.service;

import br.com.f1rst.cep_consulta_service.dto.CepDto;
import org.springframework.stereotype.Service;

@Service
public class CepService {
    //consumo a api via cep
    //montar url usando o cep passado
    //gravar o json e data da consulta no banco


    public String buscarCep(CepDto cep) {

        return "Buscar pedido no viacep " + cep.getCep();
    }

    public String guardaLogs(String data) {
        return data;
    }
}


