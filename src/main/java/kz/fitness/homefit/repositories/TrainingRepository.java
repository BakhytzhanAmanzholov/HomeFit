package kz.fitness.homefit.repositories;

import kz.fitness.homefit.models.Training;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainingRepository extends JpaRepository<Training, Long> {
}
