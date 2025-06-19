package ge.tbc.testautomation.data.models.responses.planets;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Result(

	@JsonProperty("uid")
	String uid,

	@JsonProperty("__v")
	Integer v,

	@JsonProperty("description")
	String description,

	@JsonProperty("_id")
	String id,

	@JsonProperty("properties")
	Properties properties
) {
}