package br.com.f1rst.cep_consulta_service.exception;

import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class CepErrorResponse {
    private String message;
    private Integer status;

}
