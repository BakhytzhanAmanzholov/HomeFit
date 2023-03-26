package kz.fitness.homefit.controllers;

import kz.fitness.homefit.dto.mappers.AccountMapper;
import kz.fitness.homefit.dto.request.RegistrationDto;
import kz.fitness.homefit.models.Account;
import kz.fitness.homefit.services.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
