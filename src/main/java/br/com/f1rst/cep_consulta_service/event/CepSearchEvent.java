package br.com.f1rst.cep_consulta_service.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CepSearchEvent {

    private String cep;
    private String responseJson;
}
