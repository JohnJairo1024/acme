package co.com.acme.application.service;

import co.com.acme.domain.model.EnvioResultado;
import co.com.acme.domain.model.Pedido;
import co.com.acme.domain.port.EnvioPedidoGateway;
import org.springframework.stereotype.Service;

@Service
public class EnviarPedidoService implements EnviarPedidoUseCase {

    private final EnvioPedidoGateway envioPedidoGateway;

    public EnviarPedidoService(EnvioPedidoGateway envioPedidoGateway) {
        this.envioPedidoGateway = envioPedidoGateway;
    }

    @Override
    public EnvioResultado enviarPedido(Pedido pedido) {
        return envioPedidoGateway.enviar(pedido);
    }
}