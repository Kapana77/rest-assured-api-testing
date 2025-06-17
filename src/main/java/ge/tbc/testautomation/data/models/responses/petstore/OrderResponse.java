package ge.tbc.testautomation.data.models.responses.petstore;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.ToString;

import java.time.OffsetDateTime;
@ToString
@Data
public class OrderResponse {
    private Long id;
    private String status;
    private Boolean complete;
    private Integer quantity;
    private Long petId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS[XXX][X]")
    private OffsetDateTime shipDate;
}
