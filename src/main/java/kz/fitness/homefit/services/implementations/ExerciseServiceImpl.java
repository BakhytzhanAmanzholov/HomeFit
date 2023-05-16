package kz.fitness.homefit.services.implementations;

import com.fasterxml.jackson.databind.ObjectMapper;
import kz.fitness.homefit.dto.request.TrainingDto;
import kz.fitness.homefit.dto.response.VideoDto;
import kz.fitness.homefit.exceptions.NotFoundException;
import kz.fitness.homefit.models.Account;
import kz.fitness.homefit.models.Exercise;
import kz.fitness.homefit.models.Training;
import kz.fitness.homefit.repositories.ExerciseRepository;
import kz.fitness.homefit.services.AccountService;
import kz.fitness.homefit.services.ExerciseService;
import kz.fitness.homefit.services.TrainingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.rmi.server.UID;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ExerciseServiceImpl implements ExerciseService {

    private final ExerciseRepository exerciseRepository;

    private final AccountService accountService;
    private final TrainingService trainingService;
    private final RestTemplate restTemplate;

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
    public void train(TrainingDto dto) {
        Account account = accountService.findByEmail(accountService.isLogged());
        byte[] decodedBytes = Base64.getDecoder().decode(dto.getVideo().getBytes());
        String name = UUID.randomUUID().toString() + ".mp4";
        try {
            FileOutputStream out = new FileOutputStream("ml-server\\" + name);
            out.write(decodedBytes);
            out.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        ResponseEntity<Object> responseToServe = restTemplate.getForEntity("https://64.226.81.168:5000/curl?video=" + name, Object.class);
        Object objects = responseToServe.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        VideoDto videoLocation = objectMapper.convertValue(objects, VideoDto.class);
        System.out.println(videoLocation.getOutput_video());
        Training training = trainingService.save(
                Training.builder()
                        .account(account)
                        .location(videoLocation.getOutput_video())
                        .build()
        );
        accountService.addTrainingToAccount(training, account);
    }
}
