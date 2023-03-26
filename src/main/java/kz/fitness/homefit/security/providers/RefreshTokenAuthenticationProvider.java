package kz.fitness.homefit.security.providers;

import com.auth0.jwt.exceptions.JWTVerificationException;
import kz.fitness.homefit.security.authentication.RefreshTokenAuthentication;
import kz.fitness.homefit.security.config.JwtSecurityConfig;
import kz.fitness.homefit.security.exceptions.RefreshTokenException;
import kz.fitness.homefit.security.repository.BlackListRepository;
import kz.fitness.homefit.security.utils.JwtUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Component
@Slf4j
@ConditionalOnBean(value = JwtSecurityConfig.class)
public class RefreshTokenAuthenticationProvider implements AuthenticationProvider {

    JwtUtil jwtUtil;

    BlackListRepository blacklistRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String token = (String)authentication.getCredentials();

        if (blacklistRepository.exists(token)) {
            throw new RefreshTokenException("Token was revoked");
        }
        try {
            return jwtUtil.buildAuthentication(token);
        } catch (JWTVerificationException e) {
            log.info(e.getMessage());
            throw new RefreshTokenException(e.getMessage(), e);
        }

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return RefreshTokenAuthentication.class.isAssignableFrom(authentication);
    }
}

