package kz.fitness.homefit.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountDto {
    private Long id;
    private String email;
    private String fullName;
    private Integer count;
    private Integer calories;

    private String gender;
    private Integer age;
}
