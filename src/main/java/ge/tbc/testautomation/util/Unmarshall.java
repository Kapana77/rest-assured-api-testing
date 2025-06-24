package ge.tbc.testautomation.util;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import org.w3c.dom.Document;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Unmarshall {
    public static <T> T unmarshallResponse(String response, Class<T> object) {
        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(response.getBytes(StandardCharsets.UTF_8));
            SOAPMessage message = MessageFactory.newInstance().createMessage(null, byteArrayInputStream);
            Document document = message.getSOAPBody().extractContentAsDocument();
            Unmarshaller unmarshaller = JAXBContext.newInstance(object).createUnmarshaller();
            Object unmarshal = unmarshaller.unmarshal(document);
            return object.cast(unmarshal);
        } catch (SOAPException | JAXBException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}