package co.com.acme.controller;

import co.com.acme.application.service.EnviarPedidoUseCase;
import co.com.acme.domain.model.EnvioResultado;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PedidoControllerTest {

    private final EnviarPedidoUseCase enviarPedidoUseCase = mock(EnviarPedidoUseCase.class);
    private final MockMvc mockMvc = MockMvcBuilders
            .standaloneSetup(new PedidoController(enviarPedidoUseCase, new PedidoApiMapper()))
            .setControllerAdvice(new ApiExceptionHandler())
            .build();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void shouldTransformJsonRequestIntoJsonResponse() throws Exception {
        when(enviarPedidoUseCase.enviarPedido(any())).thenReturn(new EnvioResultado(
                "80375472",
                "Entregado exitosamente al cliente"));

        String requestBody = objectMapper.writeValueAsString(new java.util.LinkedHashMap<>() {{
            put("enviarPedido", new java.util.LinkedHashMap<>() {{
                put("numPedido", "75630275");
                put("cantidadPedido", "1");
                put("codigoEAN", "00110000765191002104587");
                put("nombreProducto", "Armario INVAL");
                put("numDocumento", "1113987400");
                put("direccion", "CR 72B 45 12 APT 301");
            }});
        }});

        mockMvc.perform(post("/api/v1/pedidos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.enviarPedidoRespuesta.codigoEnvio").value("80375472"))
                .andExpect(jsonPath("$.enviarPedidoRespuesta.estado").value("Entregado exitosamente al cliente"));
    }

    @Test
    void shouldReturnValidationErrorWhenPayloadIsInvalid() throws Exception {
        String requestBody = "{\"enviarPedido\":{\"numPedido\":\"\"}}";

        mockMvc.perform(post("/api/v1/pedidos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.codigo").value("VALIDATION_ERROR"));
    }
}