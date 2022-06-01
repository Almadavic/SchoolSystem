package application.entity;

import application.entity.users.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "tb_registrations")
public class Registration {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
    private Instant registrationMoment;


    @OneToOne()
    @MapsId
    @JsonIgnore
    @Setter(AccessLevel.NONE)
    private User user;


    public Registration(Instant registrationMoment, User user) {
        this.registrationMoment=registrationMoment;
        this.user=user;
    }
}
