package kz.fitness.homefit.controllers;

import kz.fitness.homefit.dto.mappers.ExerciseMapper;
import kz.fitness.homefit.dto.request.TrainingDto;
import kz.fitness.homefit.dto.response.ExerciseDto;
import kz.fitness.homefit.models.Exercise;
import kz.fitness.homefit.services.ExerciseService;
import kz.fitness.homefit.services.TrainingService;
import kz.fitness.homefit.services.implementations.ExerciseServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/exercises")
public class ExerciseController {
    private final ExerciseService exerciseService;


    @GetMapping
    public ResponseEntity<?> findAll() {
        List<Exercise> exerciseList = exerciseService.findAll();
        List<ExerciseDto> dtoList = new ArrayList<>();
        for (Exercise exercise : exerciseList) {
            dtoList.add(ExerciseMapper.toResponseDto(exercise));
        }
        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }

    @PostMapping("{id}")
    public ResponseEntity<?> findById(@RequestBody TrainingDto dto) {
        exerciseService.train(dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
