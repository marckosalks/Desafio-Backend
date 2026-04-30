package br.com.f1rst.cep_consulta_service.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class CepResponseDto {

    private String cep;
    private String logradouro;
    private String bairro;
    private String cidade;
    private String uf;

}
