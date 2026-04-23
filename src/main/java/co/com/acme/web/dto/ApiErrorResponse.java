package co.com.acme.web.dto;

import java.time.OffsetDateTime;
import java.util.Map;

public record ApiErrorResponse(
        String codigo,
        String mensaje,
        OffsetDateTime timestamp,
        Map<String, String> detalles) {
}