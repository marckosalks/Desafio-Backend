package br.com.f1rst.cep_consulta_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ViaCepResponse {

    private String cep;
    private String logradouro;
    private String bairro;
    private String localidade;
    private String uf;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String erro;
}
