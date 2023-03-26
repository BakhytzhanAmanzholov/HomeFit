package kz.fitness.homefit.models;

import lombok.*;

import javax.persistence.*;

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

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String fullName;
    private String email;
    private String password;
    private String gender;
    private Integer age;

    private Boolean isNotBanned;

    @Enumerated(value = EnumType.STRING)
    private Role role;
}
