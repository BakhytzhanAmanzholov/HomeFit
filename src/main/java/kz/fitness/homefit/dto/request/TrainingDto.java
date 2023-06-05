package kz.fitness.homefit.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TrainingDto {
    private String video;

    public TrainingDto(String video) {
        this.video = video;
    }

    public TrainingDto() {
    }
}
