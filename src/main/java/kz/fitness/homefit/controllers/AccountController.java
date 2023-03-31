package kz.fitness.homefit.controllers;

import kz.fitness.homefit.dto.mappers.AccountMapper;
import kz.fitness.homefit.dto.mappers.TrainingMapper;
import kz.fitness.homefit.dto.request.RegistrationDto;
import kz.fitness.homefit.dto.response.TrainingHistoryDto;
import kz.fitness.homefit.models.Account;
import kz.fitness.homefit.models.Training;
import kz.fitness.homefit.services.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    public ResponseEntity<?> createAccount(@RequestBody RegistrationDto dto) {
        try {
            Account account = accountService.save(AccountMapper.fromRequestDto(dto));
            return new ResponseEntity<>("The account " + account.getEmail() +
                    " successfully registered", HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Email is taken", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<?> findByEmail(@PathVariable("email") String email) {
        return new ResponseEntity<>(AccountMapper.toResponseDto(accountService.findByEmail(email)), HttpStatus.OK);
    }

    @GetMapping("/history")
    public ResponseEntity<?> history() {
        Set<Training> trainingList = accountService.getHistory();
        List<TrainingHistoryDto> dtoList = new ArrayList<>();

        for (Training training : trainingList) {
            dtoList.add(TrainingMapper.toResponseDto(training));
        }

        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }
}
