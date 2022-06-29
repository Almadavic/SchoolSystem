package application.dto.response;

import application.vo.RegistrationVO;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Getter
@NoArgsConstructor
@JsonPropertyOrder(value = {"registrationMoment"})
public class RegistrationDto { //DTO da classe Registration
    @JsonProperty(value = "registrationMoment")
    private Instant registrationMoment;

    public RegistrationDto(RegistrationVO registration) {
        this.registrationMoment = registration.getRegistrationMoment();
    }

}
