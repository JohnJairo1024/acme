package co.com.acme.infrastructure.client;

import co.com.acme.domain.model.EnvioResultado;
import co.com.acme.domain.model.Pedido;
import co.com.acme.domain.port.EnvioPedidoGateway;
import co.com.acme.infrastructure.exception.IntegrationException;
import co.com.acme.infrastructure.xml.SoapRequestXmlBuilder;
import co.com.acme.infrastructure.xml.SoapResponseXmlParser;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

@Component
public class SoapPedidoGateway implements EnvioPedidoGateway {

    private final RestClient soapRestClient;
    private final SoapRequestXmlBuilder soapRequestXmlBuilder;
    private final SoapResponseXmlParser soapResponseXmlParser;

    public SoapPedidoGateway(RestClient soapRestClient,
                             SoapRequestXmlBuilder soapRequestXmlBuilder,
                             SoapResponseXmlParser soapResponseXmlParser) {
        this.soapRestClient = soapRestClient;
        this.soapRequestXmlBuilder = soapRequestXmlBuilder;
        this.soapResponseXmlParser = soapResponseXmlParser;
    }

    @Override
    public EnvioResultado enviar(Pedido pedido) {
        String requestXml = soapRequestXmlBuilder.build(pedido);

        try {
            String responseXml = soapRestClient.post()
                    .contentType(MediaType.TEXT_XML)
                    .accept(MediaType.TEXT_XML, MediaType.APPLICATION_XML)
                    .body(requestXml)
                    .retrieve()
                    .body(String.class);

            if (responseXml == null || responseXml.isBlank()) {
                throw new IntegrationException("El servicio externo respondio sin contenido");
            }

            return soapResponseXmlParser.parse(responseXml);
        } catch (RestClientException exception) {
            throw new IntegrationException("Error consumiendo el servicio externo de envios ACME", exception);
        }
    }
}