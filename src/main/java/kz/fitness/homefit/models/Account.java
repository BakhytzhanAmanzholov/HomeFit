package kz.fitness.homefit.models;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Account {
    public enum Role {
        USER, ADMIN
    }

    public enum Gender {
        MALE, FEMALE
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String fullName;
    private String email;
    private String password;
    @Enumerated(value = EnumType.STRING)
    private Gender gender;
    private Integer age;

    private Boolean isNotBanned;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    @OneToMany
    private Set<Training> trainings;
}
