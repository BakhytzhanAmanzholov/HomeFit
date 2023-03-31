package kz.fitness.homefit.services.implementations;

import kz.fitness.homefit.exceptions.NotFoundException;
import kz.fitness.homefit.models.Training;
import kz.fitness.homefit.repositories.TrainingRepository;
import kz.fitness.homefit.services.TrainingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class TrainingServiceImpl implements TrainingService {

    private final TrainingRepository trainingRepository;

    @Override
    public Training save(Training entity) {
        return trainingRepository.save(entity);
    }

    @Override
    public void delete(Long aLong) {
        trainingRepository.deleteById(aLong);
    }

    @Override
    public Training update(Training entity) {
        return null;
    }

    @Override
    public Training findById(Long aLong) {
        return trainingRepository.findById(aLong).orElseThrow(
                () -> new NotFoundException("Exercise by id <" + aLong + "> not found"));
    }
}
