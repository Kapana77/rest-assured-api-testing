package ge.tbc.testautomation.data.models.requests.petstore;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.OffsetDateTimeSerializer;
import lombok.Data;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.OffsetDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "status", "complete", "petId", "id", "quantity"})
@Data
@Accessors(chain = true)
public class OrderRequest {
    private Long id;
    private Long petId;
    private Integer quantity;
    private String status;
    private Boolean complete;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX")
    @JsonSerialize(using = OffsetDateTimeSerializer.class)
    private OffsetDateTime shipDate;

}
