package co.com.acme.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record EnviarPedidoRequest(
        @Valid
        @NotNull
        @JsonProperty("enviarPedido")
        EnviarPedidoPayload enviarPedido) {
}