package kz.fitness.homefit;

import com.fasterxml.jackson.databind.ObjectMapper;
import kz.fitness.homefit.dto.request.TrainingDto;
import kz.fitness.homefit.models.Account;
import kz.fitness.homefit.models.Exercise;
import kz.fitness.homefit.models.Training;
import kz.fitness.homefit.services.AccountService;
import kz.fitness.homefit.services.ExerciseService;
import kz.fitness.homefit.services.TrainingService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.HashSet;

@SpringBootApplication
public class HomeFitApplication {

    @Bean
    public RestTemplate restTemplate(){
        RestTemplate restTemplate = new RestTemplate();
        return new RestTemplate();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    public static void main(String[] args) {
        SpringApplication.run(HomeFitApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ExerciseService exerciseService, AccountService accountService,
                                               TrainingService trainingService) {
        return args -> {
            exerciseService.save(
                    Exercise.builder()
                            .name("Dumbells")
                            .type(Exercise.Type.HANDS)
                            .build()
            );

            Exercise squats = exerciseService.save(
                    Exercise.builder()
                            .name("Squats")
                            .type(Exercise.Type.LEGS)
                            .build()
            );

            exerciseService.save(
                    Exercise.builder()
                            .name("Pushups")
                            .type(Exercise.Type.HANDS)
                            .build()
            );

            Account account = accountService.save(
                    Account.builder()
                            .age(20)
                            .email("abc123@gmail.com")
                            .password("abc123")
                            .fullName("Hello")
                            .gender(Account.Gender.MALE)
                            .count(1)
                            .calories(32)
                            .trainings(new HashSet<>())
                            .build()
            );
            Training training = trainingService.save(
                    Training.builder()
                            .account(account)
                            .location("ml-server/video/dca954aa-695e-4d8b-9fc4-13dea4ab21f3_processed.mp4")
                            .time(14L)
                            .exercise(squats)
                            .dateTime(LocalDateTime.now())
                            .build()
            );
            account = accountService.findByEmail(account.getEmail());
            accountService.addTrainingToAccount(training, account);
        };
    }

}
