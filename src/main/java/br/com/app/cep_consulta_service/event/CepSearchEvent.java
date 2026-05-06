package br.com.app.cep_consulta_service.event;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CepSearchEvent {

    private String cep;
    private String responseJson;
}
