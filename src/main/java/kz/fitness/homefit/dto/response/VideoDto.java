package kz.fitness.homefit.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class VideoDto {

    public VideoDto(String output_video, Integer count, Integer accuracy, List<ErrorDto> error) {
        this.output_video = output_video;
        this.count = count;
        this.accuracy = accuracy;
        this.error = error;
    }

    public VideoDto() {
    }

    public VideoDto(String output_video, Integer count, Integer accuracy) {
        this.output_video = output_video;
        this.count = count;
        this.accuracy = accuracy;
    }

    public VideoDto(String output_video) {
        this.output_video = output_video;
    }

    @JsonProperty(namespace = "output_video")
    private String output_video; //output_video

    @JsonProperty(namespace = "count")
    private Integer count;

    @JsonProperty(namespace = "count")
    private Integer accuracy;

    @JsonProperty(namespace = "error")
    private List<ErrorDto> error;
}
