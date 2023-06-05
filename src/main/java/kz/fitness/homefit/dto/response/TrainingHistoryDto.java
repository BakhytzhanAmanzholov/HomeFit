package kz.fitness.homefit.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TrainingHistoryDto {
    private Long id;
    private String dateTime;
    private String exercise;
    private String src;
    private String video;
}
