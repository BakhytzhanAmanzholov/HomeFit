package kz.fitness.homefit.services.implementations;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xuggle.xuggler.IContainer;
import com.xuggle.xuggler.IStream;
import com.xuggle.xuggler.IStreamCoder;
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
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
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
    public void train(TrainingDto dto, Long id) {
        Account account = accountService.findByEmail(accountService.isLogged());
        byte[] decodedBytes = Base64.getDecoder().decode(dto.getVideo().getBytes());
        String name = UUID.randomUUID() + ".mp4";
        try {
            FileOutputStream out = new FileOutputStream("\\root\\HomeFit\\ml-server\\" + name);
            out.write(decodedBytes);
            System.out.println(out.getFD().toString());
            out.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        ResponseEntity<Object> responseToServe;
        if (id == 1) {
            responseToServe = restTemplate.getForEntity("http://161.35.223.202:5000/curl?video=" + name, Object.class);
        } else if (id == 3) {
            responseToServe = restTemplate.getForEntity("http://161.35.223.202:5000/pushups?video=" + name, Object.class);
        } else {
            responseToServe = restTemplate.getForEntity("http://161.35.223.202:5000/squats?video=" + name, Object.class);
        }
        Object objects = responseToServe.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        VideoDto videoLocation = objectMapper.convertValue(objects, VideoDto.class);
        String filename = videoLocation.getOutput_video();
        IContainer container = IContainer.make();
        container.open(filename, IContainer.Type.READ, null);
        long duration = container.getDuration() / 1000000;
        container.close();


        Training training = trainingService.save(
                Training.builder()
                        .account(account)
                        .location(videoLocation.getOutput_video())
                        .dateTime(LocalDateTime.now())
                        .time(duration)
                        .exercise(findById(id))
                        .build()
        );
        account = accountService.findByEmail(accountService.isLogged());
        accountService.addTrainingToAccount(training, account);
        account = accountService.findByEmail(accountService.isLogged());
        account.setCount(account.getCount() + 1);
        account = accountService.findByEmail(accountService.isLogged());
        if(id == 1){
            account.setCalories((int) (account.getCalories() + duration/3));
        } else if (id == 2) {
            account.setCalories((int) (account.getCalories() + (duration/3)*2));
        }
        else {
            account.setCalories((int) (account.getCalories() + duration/3));
        }
    }
}
