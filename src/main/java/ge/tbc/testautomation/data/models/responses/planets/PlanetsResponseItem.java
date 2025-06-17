package ge.tbc.testautomation.data.models.responses.planets;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PlanetsResponseItem(

	@JsonProperty("uid")
	String uid,

	@JsonProperty("name")
	String name,

	@JsonProperty("url")
	String url
) {
}