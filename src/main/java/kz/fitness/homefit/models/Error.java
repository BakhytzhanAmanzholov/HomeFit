package kz.fitness.homefit.models;

import lombok.*;
import org.springframework.context.annotation.Lazy;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Error {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Integer count;
    private String field;
    private String localizedMessage;
    private String message;

    @ManyToOne
    @Lazy
    @ToString.Exclude
    private Training training;
}
