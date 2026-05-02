package br.com.f1rst.cep_consulta_service.controller;

import br.com.f1rst.cep_consulta_service.dto.CepDto;
import br.com.f1rst.cep_consulta_service.dto.ConsultaResponse;
import br.com.f1rst.cep_consulta_service.exception.CepNotFoundException;
import br.com.f1rst.cep_consulta_service.service.CepService;
import br.com.f1rst.cep_consulta_service.utils.CepUtils;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/consulta")
@Validated
public class ConsultaController {

    private final CepService cepService;

    public ConsultaController(CepService cepService){
        this.cepService = cepService;
    }

    @GetMapping("/{cep:.+}")
    @ResponseStatus(HttpStatus.OK)
    public ConsultaResponse consultaCep (@PathVariable @Valid @Pattern(regexp = "^\\d{8}$", message = "CEP inválido") String cep) throws CepNotFoundException {

        String cepLimpo = CepUtils.limpar(cep);

        CepDto dto = new CepDto(cepLimpo);

        return cepService.buscarCep(dto);
    }
}

