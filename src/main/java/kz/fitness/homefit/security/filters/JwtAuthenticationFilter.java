package kz.fitness.homefit.security.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import kz.fitness.homefit.security.authentication.RefreshTokenAuthentication;
import kz.fitness.homefit.security.config.JwtSecurityConfig;
import kz.fitness.homefit.security.details.UserDetailsImpl;
import kz.fitness.homefit.security.utils.AuthorizationHeaderUtil;
import kz.fitness.homefit.security.utils.JwtUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

import static kz.fitness.homefit.constants.GlobalApplicationConstants.AUTHENTICATION_URL;


@Component
@Slf4j
@ConditionalOnBean(value = JwtSecurityConfig.class)
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    public static final String USERNAME_PARAMETER = "email";

    private final ObjectMapper objectMapper;

    private final JwtUtil jwtUtil;

    private final AuthorizationHeaderUtil authorizationHeaderUtil;

    public JwtAuthenticationFilter(ObjectMapper objectMapper,
                                   JwtUtil jwtUtil,
                                   AuthorizationHeaderUtil authorizationHeaderUtil,
                                   AuthenticationConfiguration authenticationConfiguration) throws Exception {
        super(authenticationConfiguration.getAuthenticationManager());
        this.setUsernameParameter(USERNAME_PARAMETER);
        this.setFilterProcessesUrl(AUTHENTICATION_URL);
        this.objectMapper = objectMapper;
        this.jwtUtil = jwtUtil;
        this.authorizationHeaderUtil = authorizationHeaderUtil;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (hasRefreshToken(request)) {
            String refreshToken = authorizationHeaderUtil.getToken(request);
            RefreshTokenAuthentication refreshTokenAuthentication = new RefreshTokenAuthentication(refreshToken);
            return super.getAuthenticationManager().authenticate(refreshTokenAuthentication);
        } else {
            return super.attemptAuthentication(request, response);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {
        writeTokens(request, response, (UserDetailsImpl) authResult.getPrincipal());
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
        log.info(failed.toString());
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
    }

    private void writeTokens(HttpServletRequest request, HttpServletResponse response, UserDetailsImpl userDetails) throws IOException {
        response.setContentType("application/json");

        AuthData r = new AuthData();
        r.setAge(userDetails.getUser().getAge());
        r.setEmail(userDetails.getUser().getEmail());
        r.setGender(userDetails.getUser().getGender());
        r.setFullName(userDetails.getUser().getFullName());

        Map<String, String> tokenJson = jwtUtil.generateTokens(
                userDetails.getUsername(),
                userDetails.getAuthorities().iterator().next().getAuthority(),
                request.getRequestURL().toString());
        r.setToken(tokenJson);

        objectMapper.writeValue(response.getOutputStream(), r);
    }

    private boolean hasRefreshToken(HttpServletRequest request) {
        return authorizationHeaderUtil.hasAuthorizationToken(request);
    }

}

@Data
class AuthData {
    private Map<String, String> token;
    private String email;
    private String fullName;
    private String gender;
    private Integer age;
}

