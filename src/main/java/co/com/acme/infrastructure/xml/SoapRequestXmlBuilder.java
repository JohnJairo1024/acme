package co.com.acme.infrastructure.xml;

import co.com.acme.domain.model.Pedido;
import co.com.acme.infrastructure.exception.IntegrationException;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;

@Component
public class SoapRequestXmlBuilder {

    private static final String SOAP_ENV_NAMESPACE = "http://schemas.xmlsoap.org/soap/envelope/";
    private static final String ENV_NAMESPACE = "http://WSDLs/EnvioPedidos/EnvioPedidosAcme";

    public String build(Pedido pedido) {
        try {
            var factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            Document document = factory.newDocumentBuilder().newDocument();

            Element envelope = document.createElementNS(SOAP_ENV_NAMESPACE, "soapenv:Envelope");
            envelope.setAttribute("xmlns:env", ENV_NAMESPACE);
            document.appendChild(envelope);

            Element header = document.createElementNS(SOAP_ENV_NAMESPACE, "soapenv:Header");
            envelope.appendChild(header);

            Element body = document.createElementNS(SOAP_ENV_NAMESPACE, "soapenv:Body");
            envelope.appendChild(body);

            Element envioPedidoAcme = document.createElementNS(ENV_NAMESPACE, "env:EnvioPedidoAcme");
            body.appendChild(envioPedidoAcme);

            Element envioPedidoRequest = document.createElement("EnvioPedidoRequest");
            envioPedidoAcme.appendChild(envioPedidoRequest);

            appendElement(document, envioPedidoRequest, "pedido", pedido.numeroPedido());
            appendElement(document, envioPedidoRequest, "Cantidad", pedido.cantidad());
            appendElement(document, envioPedidoRequest, "EAN", pedido.codigoEan());
            appendElement(document, envioPedidoRequest, "Producto", pedido.nombreProducto());
            appendElement(document, envioPedidoRequest, "Cedula", pedido.numeroDocumento());
            appendElement(document, envioPedidoRequest, "Direccion", pedido.direccion());

            var transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            StringWriter writer = new StringWriter();
            transformer.transform(new DOMSource(document), new StreamResult(writer));
            return writer.toString();
        } catch (Exception exception) {
            throw new IntegrationException("No fue posible construir la solicitud SOAP/XML", exception);
        }
    }

    private void appendElement(Document document, Element parent, String name, String value) {
        Element element = document.createElement(name);
        element.setTextContent(value);
        parent.appendChild(element);
    }
}