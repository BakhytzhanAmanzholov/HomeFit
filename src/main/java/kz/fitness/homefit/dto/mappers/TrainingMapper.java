package kz.fitness.homefit.dto.mappers;

import kz.fitness.homefit.dto.response.ErrorDto;
import kz.fitness.homefit.dto.response.TrainingHistoryDto;
import kz.fitness.homefit.dto.response.TrainingIdDto;
import kz.fitness.homefit.models.Error;
import kz.fitness.homefit.models.Training;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class TrainingMapper {
    public static TrainingHistoryDto toResponseDto(Training training) {
        TrainingHistoryDto dto = TrainingHistoryDto.builder()
                .id(training.getId())
                .dateTime(training.getDateTime().toString())
                .errors(new ArrayList<>())
                .exercise(training.getExercise().getName())
                .calories(training.getCalories())
                .accuracy(training.getAccuracy())
                .count(training.getCount())
                .build();
        if (training.getVideo() != null) {
            String s = new String(training.getVideo(), StandardCharsets.UTF_8);
            dto.setVideo(s);
        }
        List<ErrorDto> errorDto = new ArrayList<>();
        for (Error error: training.getErrors()){
            errorDto.add(
                    ErrorDto.builder()
                            .count(error.getCount())
                            .field(error.getField())
                            .localizedMessage(error.getLocalizedMessage())
                            .message(error.getMessage())
                            .build()
            );
        }
        if (training.getExercise().getName().equals("Squats")) {
            dto.setSrc("assets/images/history/squats.jpg");
        } else if (training.getExercise().getName().equals("Dumbells")) {
            dto.setSrc("assets/images/history/curls.jpg");
        } else if (training.getExercise().getName().equals("Pushups")) {
            dto.setSrc("assets/images/history/pushups.jpg");
        }
        dto.setErrors(errorDto);

        return dto;
    }

    public static TrainingIdDto toResponseDtoHistory(Training training) {
        TrainingIdDto dto = TrainingIdDto.builder()
                .id(training.getId())
                .exercise(training.getExercise().getName())
                .dateTime(training.getDateTime().toString())
                .build();
        if (training.getExercise().getName().equals("Squats")) {
            dto.setSrc("assets/images/history/squats.jpg");
        } else if (training.getExercise().getName().equals("Dumbells")) {
            dto.setSrc("assets/images/history/curls.jpg");
        } else if (training.getExercise().getName().equals("Pushups")) {
            dto.setSrc("assets/images/history/pushups.jpg");
        }

        return dto;
    }
}
