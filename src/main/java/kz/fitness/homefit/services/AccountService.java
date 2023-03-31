package kz.fitness.homefit.services;


import kz.fitness.homefit.models.Account;
import kz.fitness.homefit.models.Training;

import java.util.List;
import java.util.Set;

public interface AccountService extends CrudService<Account, Long>{
    Account findByEmail(String email);

    List<Account> findAll();

    String isLogged();

    void addTrainingToAccount(Training training, Account account);
    Set<Training> getHistory();
}
