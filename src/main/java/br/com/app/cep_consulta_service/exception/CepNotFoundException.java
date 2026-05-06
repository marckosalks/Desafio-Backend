package br.com.app.cep_consulta_service.exception;

public class CepNotFoundException extends Exception {

    public CepNotFoundException(String message) {
        super(message);
    }
}