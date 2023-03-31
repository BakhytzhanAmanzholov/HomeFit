package kz.fitness.homefit.services;

import kz.fitness.homefit.dto.request.TrainingDto;
import kz.fitness.homefit.models.Exercise;

import java.util.List;

public interface ExerciseService extends CrudService<Exercise, Long>{
    List<Exercise> findAll();

    void train(TrainingDto dto);
}
