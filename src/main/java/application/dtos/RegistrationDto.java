package application.dtos;

import application.entities.Registration;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Getter
@NoArgsConstructor
@JsonPropertyOrder(value = {"registrationMoment"})
public class RegistrationDto {

    @JsonProperty(value = "registrationMoment")
    private Instant registrationMoment;

    public RegistrationDto(Registration registration) {
        this.registrationMoment=registration.getRegistrationMoment();
    }

}
