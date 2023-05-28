package kz.fitness.homefit.dto.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TrainingIdDto {
    private Long id;
    private String dateTime;
    private String exercise;
}
