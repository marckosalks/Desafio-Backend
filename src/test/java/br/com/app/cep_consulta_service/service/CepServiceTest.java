package br.com.app.cep_consulta_service.service;

import br.com.app.cep_consulta_service.client.AgenciaClient;
import br.com.app.cep_consulta_service.client.ViaCepClient;
import br.com.app.cep_consulta_service.dto.*;
import br.com.app.cep_consulta_service.event.CepSearchEvent;
import br.com.app.cep_consulta_service.exception.CepNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CepServiceTest {

    @Mock
    private ViaCepClient viaCepClient;
    @Mock
    private AgenciaClient agenciaClient;
    @Mock
    private ApplicationEventPublisher eventPublisher;

    private CepService cepService;
    private CepDto cepDto;
    private ViaCepResponse viaCepResponse;
    private AgenciaResponse agenciaResponse;

    @BeforeEach
    void setUp() {
        cepService = new CepService(viaCepClient, agenciaClient, eventPublisher, new ObjectMapper());
        cepDto = new CepDto("05857480");
        viaCepResponse = new ViaCepResponse("05857-480", "Rua Alfonso Santi", "Parque Ligia", "São Paulo", "SP", null);
        agenciaResponse = AgenciaResponse.builder()
                .nome("Agência")
                .endereco("Rua próxima ao CEP")
                .distancia("20m")
                .build();
    }

    @Test
    @DisplayName("Deve buscar CEP com sucesso")
    void buscarCepComSucesso() throws CepNotFoundException {
        when(viaCepClient.buscarCep(anyString())).thenReturn(viaCepResponse);
        when(agenciaClient.buscarAgencia(anyString())).thenReturn(agenciaResponse);

        ConsultaResponse response = cepService.buscarCep(cepDto);

        assertNotNull(response);
        assertEquals("05857-480", response.getCep().getCep());
        assertEquals("Agência", response.getAgencia().getNome());
        verify(eventPublisher).publishEvent(isA(CepSearchEvent.class));
    }

    @Test
    @DisplayName("Deve lançar exceção quando CEP não for encontrado")
    void buscarCepNaoEncontrado() {
        viaCepResponse.setErro("true");
        when(viaCepClient.buscarCep(anyString())).thenReturn(viaCepResponse);

        assertThrows(CepNotFoundException.class, () -> cepService.buscarCep(cepDto));
        verify(agenciaClient, never()).buscarAgencia(anyString());
    }

    @Test
    @DisplayName("Deve retornar 'Não disponível' quando AgenciaClient falhar")
    void buscarCepComFalhaNaAgencia() throws CepNotFoundException {
        when(viaCepClient.buscarCep(anyString())).thenReturn(viaCepResponse);
        when(agenciaClient.buscarAgencia(anyString())).thenThrow(new RuntimeException("API Offline"));

        ConsultaResponse response = cepService.buscarCep(cepDto);

        assertNotNull(response);
        assertEquals("Não disponível", response.getAgencia().getNome());
        verify(eventPublisher).publishEvent(isA(CepSearchEvent.class));
    }
}
