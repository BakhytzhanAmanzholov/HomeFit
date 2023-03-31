package kz.fitness.homefit.dto.mappers;

import kz.fitness.homefit.dto.response.ExerciseDto;
import kz.fitness.homefit.models.Exercise;

public class ExerciseMapper {
    public static ExerciseDto toResponseDto(Exercise exercise) {
        return ExerciseDto.builder()
                .id(exercise.getId())
                .name(exercise.getName())
                .type(exercise.getType().name())
                .build();
    }
}
