package kz.fitness.homefit.dto.request;

import lombok.Data;

@Data
public class RegistrationDto {
    private String email;
    private String password;
    private String fullName;
    private String gender;
    private Integer age;
}
