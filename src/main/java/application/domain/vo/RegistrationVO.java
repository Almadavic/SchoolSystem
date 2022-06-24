package application.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@Embeddable
public class RegistrationVO { // Representa a data de cadastro do usuário no Sistema.

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
    private Instant registrationMoment;

    public RegistrationVO(Instant registrationMoment) {
        this.registrationMoment = registrationMoment;
    }
}
