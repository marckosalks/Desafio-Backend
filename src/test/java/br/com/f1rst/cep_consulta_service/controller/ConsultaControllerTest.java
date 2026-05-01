package br.com.f1rst.cep_consulta_service.controller;

import br.com.f1rst.cep_consulta_service.dto.AgenciaResponse;
import br.com.f1rst.cep_consulta_service.dto.ConsultaResponse;
import br.com.f1rst.cep_consulta_service.dto.ViaCepResponse;
import br.com.f1rst.cep_consulta_service.exception.CepNotFoundException;
import br.com.f1rst.cep_consulta_service.service.CepService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ConsultaController.class)
@ActiveProfiles("test")
class ConsultaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CepService cepService;

    @Test
    @DisplayName("Deve retornar 200 ao consultar um CEP válido")
    void deveRetornar200AoConsultarCepValido() throws Exception {
        ViaCepResponse viaCepResponse = new ViaCepResponse("05857-480", "Rua Alfonso Santi", "Parque Ligia", "São Paulo", "SP", "false");
        AgenciaResponse agenciaResponse = AgenciaResponse.builder().nome("Agência Santander").build();
        ConsultaResponse response = ConsultaResponse.builder().cep(viaCepResponse).agencia(agenciaResponse).build();

        when(cepService.buscarCep(any())).thenReturn(response);

        mockMvc.perform(get("/v1/consulta/05857480")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cep.cep").value("05857-480"))
                .andExpect(jsonPath("$.agencia.nome").value("Agência Santander"));
    }

    @Test
    @DisplayName("Deve retornar 404 quando o CEP não for encontrado")
    void deveRetornar404QuandoCepNaoEncontrado() throws Exception {
        when(cepService.buscarCep(any())).thenThrow(new CepNotFoundException("Cep Não encontrado!"));

        mockMvc.perform(get("/v1/consulta/11111111")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Cep Não encontrado!"));
    }
}
