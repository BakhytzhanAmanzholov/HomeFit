package kz.fitness.homefit.repositories;

import kz.fitness.homefit.models.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
}
