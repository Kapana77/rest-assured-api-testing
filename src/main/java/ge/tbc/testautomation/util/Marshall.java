package ge.tbc.testautomation.util;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class Marshall {
    public static <T> String marshallSoapRequest(T object) {
        try {
            MessageFactory messageFactory = MessageFactory.newInstance();
            SOAPMessage soapMessage = messageFactory.createMessage();
            SOAPPart soapPart = soapMessage.getSOAPPart();

            JAXBContext context = JAXBContext.newInstance(object.getClass());
            Marshaller marshaller = context.createMarshaller();
            marshaller.marshal(object, new DOMResult(soapPart.getEnvelope().getBody()));

            Transformer ts = TransformerFactory.newInstance().newTransformer();

            Properties properties = new Properties();
            properties.setProperty("indent", "yes");
            properties.setProperty("omit-xml-declaration", "yes");

            ts.setOutputProperties(properties);

            ByteArrayOutputStream output = new ByteArrayOutputStream();
            ts.transform(soapMessage.getSOAPPart().getContent(), new StreamResult(output));

            return output.toString(StandardCharsets.UTF_8);
        } catch (JAXBException | SOAPException | TransformerException e) {
            throw new RuntimeException(e);
        }
    }
}