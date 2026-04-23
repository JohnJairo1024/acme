# ACME API REST - Integracion Pedidos

Servicio en Java 17 con Spring Boot que expone una API REST en JSON, transforma la solicitud al formato SOAP/XML requerido por ACME y convierte la respuesta SOAP/XML nuevamente a JSON.

## Arquitectura

La solucion sigue una arquitectura por capas con estilo Ports and Adapters:

- `controller`: expone la API REST y valida el contrato HTTP.
- `application`: contiene el caso de uso `EnviarPedidoUseCase`.
- `domain`: define el modelo de negocio y el puerto `EnvioPedidoGateway`.
- `infrastructure`: implementa el consumo del servicio externo SOAP/XML y la transformacion XML.
- `web.dto`: define los contratos de entrada y salida de la API.

Principios aplicados:

- `S`: cada clase tiene una unica responsabilidad.
- `O`: el puerto `EnvioPedidoGateway` permite cambiar el proveedor externo sin tocar el caso de uso.
- `L`: cualquier implementacion del gateway puede sustituir a la actual.
- `I`: el caso de uso depende de un contrato pequeno y especifico.
- `D`: la capa de aplicacion depende de abstracciones, no de detalles HTTP/XML.

Patrones usados:

- `Ports and Adapters`
- `Mapper`
- `Facade` en el caso de uso para orquestar el envio
- `Adapter` en `SoapPedidoGateway`

## Endpoint REST

- Metodo: `POST`
- URL: `http://localhost:8080/api/v1/pedidos`
- Content-Type: `application/json`

### Request

```json
{
  "enviarPedido": {
    "numPedido": "75630275",
    "cantidadPedido": "1",
    "codigoEAN": "00110000765191002104587",
    "nombreProducto": "Armario INVAL",
    "numDocumento": "1113987400",
    "direccion": "CR 72B 45 12 APT 301"
  }
}
```

### Response

```json
{
  "enviarPedidoRespuesta": {
    "codigoEnvio": "80375472",
    "estado": "Entregado exitosamente al cliente"
  }
}
```

## Mapeo aplicado

### JSON a SOAP/XML

- `numPedido` -> `pedido`
- `cantidadPedido` -> `Cantidad`
- `codigoEAN` -> `EAN`
- `nombreProducto` -> `Producto`
- `numDocumento` -> `Cedula`
- `direccion` -> `Direccion`

### SOAP/XML a JSON

- `Codigo` -> `codigoEnvio`
- `Mensaje` -> `estado`

## Configuracion

Propiedad por defecto en `src/main/resources/application.properties`:

```properties
acme.soap.url=https://run.mocky.io/v3/19217075-6d4e-4818-98bc-416d1feb7b84
```

Tambien se puede sobreescribir con variable de entorno:

```bash
ACME_SOAP_URL=https://otro-endpoint
```

Nota: el endpoint entregado actualmente responde `404 Not Found`, por lo que la aplicacion queda lista para integrarse pero requiere una URL valida en ejecucion real.

## Ejecutar local

```bash
./mvnw spring-boot:run
```

En Windows PowerShell:

```powershell
.\mvnw.cmd spring-boot:run
```

## Pruebas

```bash
./mvnw test
```

## Docker

### Construir imagen

```bash
docker build -t acme-api .
```

### Ejecutar contenedor

```bash
docker run -p 8080:8080 -e ACME_SOAP_URL=https://otro-endpoint acme-api
```

## Repositorio

Ruta configurada del repositorio remoto:

`https://github.com/JohnJairo1024/acme.git`