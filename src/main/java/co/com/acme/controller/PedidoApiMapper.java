package co.com.acme.controller;

import co.com.acme.domain.model.EnvioResultado;
import co.com.acme.domain.model.Pedido;
import co.com.acme.web.dto.EnviarPedidoPayload;
import co.com.acme.web.dto.EnviarPedidoResponse;
import co.com.acme.web.dto.EnviarPedidoRespuestaPayload;
import org.springframework.stereotype.Component;

@Component
public class PedidoApiMapper {

    public Pedido toDomain(EnviarPedidoPayload payload) {
        return new Pedido(
                payload.numPedido(),
                payload.cantidadPedido(),
                payload.codigoEAN(),
                payload.nombreProducto(),
                payload.numDocumento(),
                payload.direccion());
    }

    public EnviarPedidoResponse toResponse(EnvioResultado resultado) {
        return new EnviarPedidoResponse(
                new EnviarPedidoRespuestaPayload(resultado.codigoEnvio(), resultado.estado()));
    }
}