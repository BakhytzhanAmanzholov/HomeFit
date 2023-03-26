package kz.fitness.homefit.dto.mappers;


import kz.fitness.homefit.dto.request.RegistrationDto;
import kz.fitness.homefit.dto.response.AccountDto;
import kz.fitness.homefit.models.Account;

public class AccountMapper {
    public static Account fromRequestDto(RegistrationDto dto){
        return Account.builder()
                .email(dto.getEmail())
                .fullName(dto.getFullName())
                .password(dto.getPassword())
                .gender(dto.getGender())
                .age(dto.getAge())
                .build();
    }

    public static AccountDto toResponseDto(Account account){
        return AccountDto.builder()
                .email(account.getEmail())
                .fullName(account.getFullName())
                .id(account.getId())
                .build();
    }
}
