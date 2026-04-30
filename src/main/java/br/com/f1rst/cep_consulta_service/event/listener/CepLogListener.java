package br.com.f1rst.cep_consulta_service.event.listener;

import br.com.f1rst.cep_consulta_service.entity.CepLog;
import br.com.f1rst.cep_consulta_service.event.CepSearchEvent;
import br.com.f1rst.cep_consulta_service.repository.CepLogRepository;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CepLogListener {

    private final CepLogRepository repository;

    public CepLogListener(CepLogRepository repository) {
        this.repository = repository;
    }

    @EventListener
    public void handleCepSearchEvent(CepSearchEvent event) {
        CepLog log = CepLog.builder()
                .cep(event.getCep())
                .response(event.getResponseJson())
                .dataConsulta(LocalDateTime.now())
                .build();

        repository.save(log);
    }
}
