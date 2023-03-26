package kz.fitness.homefit.security.details;

import kz.fitness.homefit.models.Account;
import kz.fitness.homefit.repositories.AccountRepository;
import kz.fitness.homefit.security.config.JwtSecurityConfig;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@ConditionalOnBean(value = JwtSecurityConfig.class)
public class UserDetailsServiceImpl implements UserDetailsService {

    AccountRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Account user = usersRepository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("User <" + email + "> not found"));

        return new UserDetailsImpl(user);
    }

}

