package ge.tbc.testautomation.data.models.responses.planets;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
@JsonIgnoreProperties(ignoreUnknown = true)
public record PlanetsResponse(

	@JsonProperty("results")
	List<PlanetsResponseItem> planetsResponse
) {
}