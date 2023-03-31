package kz.fitness.homefit.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VideoDto {

    public VideoDto() {
    }

    public VideoDto(String output_video) {
        this.output_video = output_video;
    }

    @JsonProperty(namespace = "output_video")
    private String output_video; //output_video
}
