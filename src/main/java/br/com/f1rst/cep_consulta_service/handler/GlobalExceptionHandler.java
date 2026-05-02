package br.com.f1rst.cep_consulta_service.handler;

import br.com.f1rst.cep_consulta_service.exception.CepErrorResponse;
import br.com.f1rst.cep_consulta_service.exception.CepNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CepNotFoundException.class)
    public ResponseEntity<CepErrorResponse> handleNotFoundException(CepNotFoundException ex){

        CepErrorResponse response = new CepErrorResponse(
                ex.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                LocalDateTime.now()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CepErrorResponse> handleGenericException(Exception ex){

        CepErrorResponse response = new CepErrorResponse(
                "Erro interno na aplicação",
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                LocalDateTime.now()
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<CepErrorResponse> handleConstraintViolationException(ConstraintViolationException ex){

        CepErrorResponse response = new CepErrorResponse(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}


