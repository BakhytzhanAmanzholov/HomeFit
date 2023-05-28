package kz.fitness.homefit.models;

import lombok.*;
import org.springframework.context.annotation.Lazy;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Training {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Account account;


    private String location;

    private LocalDateTime dateTime;

    private long time; // time in milliseconds

    @ManyToOne
    private Exercise exercise;
}
