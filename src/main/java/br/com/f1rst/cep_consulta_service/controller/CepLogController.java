package br.com.f1rst.cep_consulta_service.controller;

import br.com.f1rst.cep_consulta_service.entity.CepLog;
import br.com.f1rst.cep_consulta_service.repository.CepLogRepository;
import org.springframework.web.bind.annotation.*;

        import java.util.List;

@RestController
@RequestMapping("/v1/logs")
public class CepLogController {

    private final CepLogRepository repository;

    public CepLogController(CepLogRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<CepLog> listarLogs() {
        return repository.findAll();
    }
}