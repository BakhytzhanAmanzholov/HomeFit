package kz.fitness.homefit.security.repository.implementation;

import kz.fitness.homefit.security.config.JwtSecurityConfig;
import kz.fitness.homefit.security.repository.BlackListRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
@Slf4j
@ConditionalOnBean(value = JwtSecurityConfig.class)
public class BlacklistRepositoryImpl implements BlackListRepository {

    private final RedisTemplate<String, String> redisTemplate;


    @Override
    public void save(String token) {
        redisTemplate.opsForValue().set(token, "");
    }

    @Override
    public boolean exists(String token) {
        Boolean hasToken = redisTemplate.hasKey(token);
        return hasToken != null && hasToken;
    }

}
