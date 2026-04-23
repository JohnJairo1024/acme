package co.com.acme.application.service;

import co.com.acme.domain.model.EnvioResultado;
import co.com.acme.domain.model.Pedido;

public interface EnviarPedidoUseCase {

    EnvioResultado enviarPedido(Pedido pedido);
}