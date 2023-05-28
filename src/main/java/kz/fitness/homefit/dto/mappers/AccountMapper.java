package kz.fitness.homefit.dto.mappers;


import kz.fitness.homefit.dto.request.RegistrationDto;
import kz.fitness.homefit.dto.response.AccountDto;
import kz.fitness.homefit.models.Account;

public class AccountMapper {
    public static Account fromRequestDto(RegistrationDto dto) {
        Account account = Account.builder()
                .email(dto.getEmail())
                .fullName(dto.getFullName())
                .password(dto.getPassword())
                .age(dto.getAge())
                .build();

        if (dto.getGender().toUpperCase().equals(Account.Gender.MALE.name())) {
            account.setGender(Account.Gender.MALE);
        } else {
            account.setGender(Account.Gender.FEMALE);
        }

        return account;
    }

    public static AccountDto toResponseDto(Account account) {
        return AccountDto.builder()
                .email(account.getEmail())
                .fullName(account.getFullName())
                .id(account.getId())
                .count(account.getTrainings().size())
                .calories(account.getCalories())
                .build();
    }
}
