package co.com.acme.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record EnviarPedidoResponse(
        @JsonProperty("enviarPedidoRespuesta")
        EnviarPedidoRespuestaPayload enviarPedidoRespuesta) {
}