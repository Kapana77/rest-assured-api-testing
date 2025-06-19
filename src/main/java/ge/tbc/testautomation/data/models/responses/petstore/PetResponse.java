package ge.tbc.testautomation.data.models.responses.petstore;

import ge.tbc.testautomation.data.models.pets.xml.PetBase;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "pet")
public class PetResponse extends PetBase {
}