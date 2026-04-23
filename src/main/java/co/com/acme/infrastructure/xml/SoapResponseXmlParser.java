package co.com.acme.infrastructure.xml;

import co.com.acme.domain.model.EnvioResultado;
import co.com.acme.infrastructure.exception.IntegrationException;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;

@Component
public class SoapResponseXmlParser {

    public EnvioResultado parse(String xmlResponse) {
        try {
            var factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            Document document = factory.newDocumentBuilder().parse(new InputSource(new StringReader(xmlResponse)));

            String codigo = getElementText(document, "Codigo");
            String mensaje = getElementText(document, "Mensaje");

            if (codigo == null || mensaje == null) {
                throw new IntegrationException("La respuesta SOAP/XML no contiene los campos Codigo y Mensaje esperados");
            }

            return new EnvioResultado(codigo, mensaje);
        } catch (IntegrationException exception) {
            throw exception;
        } catch (Exception exception) {
            throw new IntegrationException("No fue posible interpretar la respuesta SOAP/XML", exception);
        }
    }

    private String getElementText(Document document, String localName) {
        var nodes = document.getElementsByTagNameNS("*", localName);
        if (nodes.getLength() == 0) {
            nodes = document.getElementsByTagName(localName);
        }

        if (nodes.getLength() == 0) {
            return null;
        }

        return nodes.item(0).getTextContent();
    }
}