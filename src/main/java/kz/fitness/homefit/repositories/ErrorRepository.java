package kz.fitness.homefit.repositories;

import kz.fitness.homefit.models.Error;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ErrorRepository extends JpaRepository<Error, Long> {
}
