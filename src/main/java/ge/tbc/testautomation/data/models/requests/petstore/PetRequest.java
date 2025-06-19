package ge.tbc.testautomation.data.models.requests.petstore;

import ge.tbc.testautomation.data.models.pets.xml.PetBase;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "pet")
public class PetRequest extends PetBase {
}