package br.com.f1rst.cep_consulta_service.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class AgenciaResponse {
    private String nome;
    private String endereco;
    private String distancia;

}
