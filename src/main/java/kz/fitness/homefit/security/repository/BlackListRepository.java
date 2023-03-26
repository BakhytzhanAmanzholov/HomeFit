package kz.fitness.homefit.security.repository;

public interface BlackListRepository {
    void save(String token);

    boolean exists(String token);

}
