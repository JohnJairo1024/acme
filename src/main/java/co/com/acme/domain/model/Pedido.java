package co.com.acme.domain.model;

public record Pedido(
        String numeroPedido,
        String cantidad,
        String codigoEan,
        String nombreProducto,
        String numeroDocumento,
        String direccion) {
}