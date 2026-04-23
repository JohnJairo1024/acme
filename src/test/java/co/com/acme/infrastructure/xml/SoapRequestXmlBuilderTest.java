package co.com.acme.infrastructure.xml;

import co.com.acme.domain.model.Pedido;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SoapRequestXmlBuilderTest {

    private final SoapRequestXmlBuilder builder = new SoapRequestXmlBuilder();

    @Test
    void shouldBuildSoapRequestWithExpectedMapping() {
        Pedido pedido = new Pedido(
                "75630275",
                "1",
                "00110000765191002104587",
                "Armario INVAL",
                "1113987400",
                "CR 72B 45 12 APT 301");

        String xml = builder.build(pedido);

        assertThat(xml).contains("soapenv:Envelope");
        assertThat(xml).contains("<pedido>75630275</pedido>");
        assertThat(xml).contains("<Cantidad>1</Cantidad>");
        assertThat(xml).contains("<EAN>00110000765191002104587</EAN>");
        assertThat(xml).contains("<Producto>Armario INVAL</Producto>");
        assertThat(xml).contains("<Cedula>1113987400</Cedula>");
        assertThat(xml).contains("<Direccion>CR 72B 45 12 APT 301</Direccion>");
    }
}