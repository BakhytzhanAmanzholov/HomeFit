package kz.fitness.homefit.models;

import lombok.*;
import org.springframework.context.annotation.Lazy;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

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


    private LocalDateTime dateTime;

    @ManyToOne
    private Exercise exercise;

    @OneToMany
    @ToString.Exclude
    private List<Error> errors;

    private Integer count;
    private Integer accuracy;
    private Integer calories;

    @Lob
    @Lazy
    @org.hibernate.annotations.Type(type = "org.hibernate.type.ImageType")
    private byte[] video;
}
