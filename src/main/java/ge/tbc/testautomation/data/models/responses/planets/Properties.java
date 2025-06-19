package ge.tbc.testautomation.data.models.responses.planets;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
public class Properties {
    @JsonProperty("orbital_period")
    private String orbitalPeriod;

    @JsonProperty("surface_water")
    private String surfaceWater;

    @JsonProperty("diameter")
    private String diameter;

    @JsonProperty("edited")
    private String edited;

    @JsonProperty("created")
    private String created;

    @JsonProperty("gravity")
    private String gravity;

    @JsonProperty("name")
    private String name;

    @JsonProperty("climate")
    private String climate;

    @JsonProperty("rotation_period")
    private String rotationPeriod;

    @JsonProperty("terrain")
    private String terrain;

    @JsonProperty("url")
    private String url;

    @JsonProperty("population")
    private String population;
}
