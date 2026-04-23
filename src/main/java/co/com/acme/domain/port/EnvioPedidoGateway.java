package co.com.acme.domain.port;

import co.com.acme.domain.model.EnvioResultado;
import co.com.acme.domain.model.Pedido;

public interface EnvioPedidoGateway {

    EnvioResultado enviar(Pedido pedido);
}