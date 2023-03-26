package kz.fitness.homefit.services;


import kz.fitness.homefit.models.Account;

public interface AccountService extends CrudService<Account, Long>{
    Account findByEmail(String email);
}
