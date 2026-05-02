package br.com.f1rst.cep_consulta_service.client;

import br.com.f1rst.cep_consulta_service.dto.ViaCepResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "viacep", url = "${viacep.url:https://viacep.com.br/ws}")
public interface ViaCepClient {

    @GetMapping("/{cep}/json/")
    ViaCepResponse buscarCep(@PathVariable String cep);
}
