package kz.fitness.homefit.services.implementations;

import kz.fitness.homefit.exceptions.NotFoundException;
import kz.fitness.homefit.models.Error;
import kz.fitness.homefit.repositories.ErrorRepository;
import kz.fitness.homefit.services.ErrorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ErrorServiceImpl implements ErrorService {

    private final ErrorRepository errorRepository;

    @Override
    public Error save(Error entity) {
        return errorRepository.save(entity);
    }

    @Override
    public void delete(Long aLong) {
        errorRepository.deleteById(aLong);
    }

    @Override
    public Error update(Error entity) {
        return null;
    }

    @Override
    public Error findById(Long aLong) {
        return errorRepository.findById(aLong).orElseThrow(
                () -> new NotFoundException("Error by id <" + aLong + "> not found"));
    }
}
