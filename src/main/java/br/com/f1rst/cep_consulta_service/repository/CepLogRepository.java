package br.com.f1rst.cep_consulta_service.repository;

import br.com.f1rst.cep_consulta_service.entity.CepLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CepLogRepository extends JpaRepository<CepLog, Long> {
}
