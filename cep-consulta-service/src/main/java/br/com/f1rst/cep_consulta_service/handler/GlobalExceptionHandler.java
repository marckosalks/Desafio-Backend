package br.com.f1rst.cep_consulta_service.handler;

import br.com.f1rst.cep_consulta_service.exception.CepErrorResponse;
import br.com.f1rst.cep_consulta_service.exception.CepNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(CepNotFoundException.class)
    public ResponseEntity<CepErrorResponse> handleNotFoundException(CepNotFoundException ex){
        CepErrorResponse response = CepErrorResponse.builder()
                .message(ex.getMessage())
                .status(HttpStatus.NOT_FOUND.value())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
}
