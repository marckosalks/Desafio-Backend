package br.com.app.cep_consulta_service.exception;

import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CepErrorResponse {

    private String message;
    private Integer status;
    private LocalDateTime timestamp;
}
