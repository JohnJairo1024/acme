package co.com.acme.web.dto;

import jakarta.validation.constraints.NotBlank;

public record EnviarPedidoPayload(
        @NotBlank(message = "numPedido es obligatorio")
        String numPedido,
        @NotBlank(message = "cantidadPedido es obligatorio")
        String cantidadPedido,
        @NotBlank(message = "codigoEAN es obligatorio")
        String codigoEAN,
        @NotBlank(message = "nombreProducto es obligatorio")
        String nombreProducto,
        @NotBlank(message = "numDocumento es obligatorio")
        String numDocumento,
        @NotBlank(message = "direccion es obligatorio")
        String direccion) {
}