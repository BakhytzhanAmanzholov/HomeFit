package kz.fitness.homefit.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class TrainingHistoryDto {
    private Long id;
    private String dateTime;
    private String exercise;
    private String src;
    private Integer count;
    private Integer calories;
    private Integer accuracy;
    private List<ErrorDto> errors;
    private String video;
}
