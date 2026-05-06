package br.com.app.cep_consulta_service.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConsultaResponse {

    private ViaCepResponse cep;
    private AgenciaResponse agencia;
}