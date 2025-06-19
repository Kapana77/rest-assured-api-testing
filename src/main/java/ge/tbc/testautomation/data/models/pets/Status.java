package ge.tbc.testautomation.data.models.pets;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Status {
    AVAILABLE("available"),
    PENDING("pending"),
    SOLD("sold");

    private final String value;

    Status(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
