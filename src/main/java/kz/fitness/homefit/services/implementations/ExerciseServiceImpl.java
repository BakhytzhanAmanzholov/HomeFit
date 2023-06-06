package kz.fitness.homefit.services.implementations;

import com.fasterxml.jackson.databind.ObjectMapper;
import kz.fitness.homefit.dto.request.TrainingDto;
import kz.fitness.homefit.dto.response.ErrorDto;
import kz.fitness.homefit.dto.response.VideoDto;
import kz.fitness.homefit.exceptions.NotFoundException;
import kz.fitness.homefit.models.Account;
import kz.fitness.homefit.models.Error;
import kz.fitness.homefit.models.Exercise;
import kz.fitness.homefit.models.Training;
import kz.fitness.homefit.repositories.ExerciseRepository;
import kz.fitness.homefit.services.AccountService;
import kz.fitness.homefit.services.ErrorService;
import kz.fitness.homefit.services.ExerciseService;
import kz.fitness.homefit.services.TrainingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ExerciseServiceImpl implements ExerciseService {

    private final ExerciseRepository exerciseRepository;

    private final AccountService accountService;
    private final TrainingService trainingService;
    private final RestTemplate restTemplate;

    private final ErrorService errorService;

    @Override
    public Exercise save(Exercise entity) {
        return exerciseRepository.save(entity);
    }

    @Override
    public void delete(Long aLong) {
        exerciseRepository.deleteById(aLong);
    }

    @Override
    public Exercise update(Exercise entity) {
        return null;
    }

    @Override
    public Exercise findById(Long aLong) {
        return exerciseRepository.findById(aLong).orElseThrow(
                () -> new NotFoundException("Exercise by id <" + aLong + "> not found"));
    }

    @Override
    public List<Exercise> findAll() {
        return exerciseRepository.findAll();
    }

    @Override
    public void train(TrainingDto dto, Long id) {
        Account account = accountService.findByEmail(accountService.isLogged());
        byte[] decodedBytes = Base64.getDecoder().decode(dto.getVideo().getBytes());
        ResponseEntity<Object> responseToServe;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("video", dto.getVideo());

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);

        if (id == 1) {
            responseToServe = restTemplate.postForEntity("http://161.35.223.202:5000/curl", requestEntity, Object.class);
        } else if (id == 3) {
            responseToServe = restTemplate.postForEntity("http://161.35.223.202:5000/pushups", requestEntity, Object.class);
        } else if (id == 2) {
            responseToServe = restTemplate.postForEntity("http://161.35.223.202:5000/squats", requestEntity, Object.class);
        } else if (id == 4) {
            responseToServe = restTemplate.postForEntity("http://161.35.223.202:5000/abs_legs", requestEntity, Object.class);
        } else {
            responseToServe = restTemplate.postForEntity("http://161.35.223.202:5000/lateral_raise", requestEntity, Object.class);
        }
        Object objects = responseToServe.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        VideoDto videoDto = objectMapper.convertValue(objects, VideoDto.class);
        List<Error> errors = new ArrayList<>();
        for (ErrorDto errorDto : videoDto.getError()) {
            errors.add(
                    errorService.save(
                            Error.builder()
                                    .count(errorDto.getCount())
                                    .field(errorDto.getField())
                                    .message(errorDto.getMessage())
                                    .localizedMessage(errorDto.getLocalizedMessage())
                                    .build()
                    )
            );
        }


        Training training = trainingService.save(
                Training.builder()
                        .account(account)
                        .dateTime(LocalDateTime.now())
                        .exercise(findById(id))
                        .video(videoDto.getOutput_video().getBytes())
                        .errors(errors)
                        .accuracy(videoDto.getAccuracy())
                        .count(videoDto.getCount())
                        .build()
        );
        account = accountService.findByEmail(accountService.isLogged());
        accountService.addTrainingToAccount(training, account);
        account = accountService.findByEmail(accountService.isLogged());
        account.setCount(account.getCount() + 1);
        account = accountService.findByEmail(accountService.isLogged());
        if (id == 1) {
            training.setCalories(videoDto.getCount());
            account.setCalories(account.getCalories() + videoDto.getCount());
        } else if (id == 2) {
            training.setCalories(videoDto.getCount());
            account.setCalories(account.getCalories() + videoDto.getCount() * 2);
        } else {
            training.setCalories(videoDto.getCount());
            account.setCalories(account.getCalories() + videoDto.getCount() / 3);
        }
    }
}
