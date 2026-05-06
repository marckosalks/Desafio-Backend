package br.com.app.cep_consulta_service.client;

import br.com.app.cep_consulta_service.dto.AgenciaResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "agencia", url = "${agencia.url:http://wiremock:8080}")
public interface AgenciaClient {

    @GetMapping("/agencia")
    AgenciaResponse buscarAgencia(@RequestParam String cep);
}
