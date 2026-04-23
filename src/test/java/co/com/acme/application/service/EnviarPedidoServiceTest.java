package co.com.acme.application.service;

import co.com.acme.domain.model.EnvioResultado;
import co.com.acme.domain.model.Pedido;
import co.com.acme.domain.port.EnvioPedidoGateway;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class EnviarPedidoServiceTest {

    private final EnvioPedidoGateway envioPedidoGateway = mock(EnvioPedidoGateway.class);
    private final EnviarPedidoService service = new EnviarPedidoService(envioPedidoGateway);

    @Test
    void shouldDelegateOrderSubmissionToGateway() {
        Pedido pedido = new Pedido(
                "75630275",
                "1",
                "00110000765191002104587",
                "Armario INVAL",
                "1113987400",
                "CR 72B 45 12 APT 301");
        EnvioResultado expected = new EnvioResultado("80375472", "Entregado exitosamente al cliente");

        when(envioPedidoGateway.enviar(pedido)).thenReturn(expected);

        EnvioResultado actual = service.enviarPedido(pedido);

        assertThat(actual).isEqualTo(expected);
        verify(envioPedidoGateway).enviar(pedido);
    }
}