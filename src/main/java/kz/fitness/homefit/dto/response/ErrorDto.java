package kz.fitness.homefit.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorDto {

    @JsonProperty(namespace = "count")
    private Integer count;

    @JsonProperty(namespace = "field")
    private String field;

    @JsonProperty(namespace = "localizedMessage")
    private String localizedMessage;

    @JsonProperty(namespace = "message")
    private String message;

    public ErrorDto() {
    }

    public ErrorDto(Integer count, String field, String localizedMessage, String message) {
        this.count = count;
        this.field = field;
        this.localizedMessage = localizedMessage;
        this.message = message;
    }

}
