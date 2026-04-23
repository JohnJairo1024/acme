package co.com.acme.infrastructure.xml;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SoapResponseXmlParserTest {

    private final SoapResponseXmlParser parser = new SoapResponseXmlParser();

    @Test
    void shouldParseSoapResponseIntoDomainObject() {
        String xml = """
                <soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:env=\"http://WSDLs/EnvioPedidos/EnvioPedidosAcme\">
                    <soapenv:Header/>
                    <soapenv:Body>
                        <env:EnvioPedidoAcmeResponse>
                            <EnvioPedidoResponse>
                                <Codigo>80375472</Codigo>
                                <Mensaje>Entregado exitosamente al cliente</Mensaje>
                            </EnvioPedidoResponse>
                        </env:EnvioPedidoAcmeResponse>
                    </soapenv:Body>
                </soapenv:Envelope>
                """;

        var result = parser.parse(xml);

        assertThat(result.codigoEnvio()).isEqualTo("80375472");
        assertThat(result.estado()).isEqualTo("Entregado exitosamente al cliente");
    }
}