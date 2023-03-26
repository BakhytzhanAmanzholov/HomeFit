package kz.fitness.homefit.services.implementations;

import kz.fitness.homefit.models.Account;
import kz.fitness.homefit.repositories.AccountRepository;
import kz.fitness.homefit.services.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public Account findByEmail(String email) {
        return accountRepository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("User by email <" + email + "> not found"));
    }

    @Override
    public Account save(Account entity) {
        try {
            findByEmail(entity.getEmail());
        }
        catch (UsernameNotFoundException e){
            entity.setPassword(passwordEncoder.encode(entity.getPassword()));
            entity.setIsNotBanned(true);
            if (entity.getRole() == null) {
                entity.setRole(Account.Role.USER);
            }
            return accountRepository.save(entity);
        }
        throw new IllegalArgumentException();
    }

    @Override
    public void delete(Long aLong) {
        accountRepository.deleteById(aLong);
    }

    @Override
    public Account update(Account entity) {
        return null;
    }

    @Override
    public Account findById(Long aLong) {
        return accountRepository.findById(aLong).orElseThrow(
                () -> new UsernameNotFoundException("User by id <" + aLong + "> not found"));
    }

    @Override
    public List<Account> findAll() {
        return accountRepository.findAll();
    }
}
