package co.com.acme.controller;

import co.com.acme.application.service.EnviarPedidoUseCase;
import co.com.acme.web.dto.EnviarPedidoRequest;
import co.com.acme.web.dto.EnviarPedidoResponse;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/pedidos", produces = MediaType.APPLICATION_JSON_VALUE)
public class PedidoController {

    private final EnviarPedidoUseCase enviarPedidoUseCase;
    private final PedidoApiMapper pedidoApiMapper;

    public PedidoController(EnviarPedidoUseCase enviarPedidoUseCase, PedidoApiMapper pedidoApiMapper) {
        this.enviarPedidoUseCase = enviarPedidoUseCase;
        this.pedidoApiMapper = pedidoApiMapper;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public EnviarPedidoResponse enviarPedido(@Valid @RequestBody EnviarPedidoRequest request) {
        var pedido = pedidoApiMapper.toDomain(request.enviarPedido());
        var resultado = enviarPedidoUseCase.enviarPedido(pedido);
        return pedidoApiMapper.toResponse(resultado);
    }
}