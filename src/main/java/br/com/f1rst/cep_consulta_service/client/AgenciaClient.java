package br.com.f1rst.cep_consulta_service.client;

import br.com.f1rst.cep_consulta_service.dto.AgenciaResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "agencia", url = "${agencia.url:http://localhost:3001}")
public interface AgenciaClient {

    @GetMapping("/agencia")
    AgenciaResponse buscarAgencia(@RequestParam String cep);
}
