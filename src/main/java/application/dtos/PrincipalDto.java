package application.dtos;

import application.entities.Responsibility;
import application.entities.users.Principal;
import application.entities.users.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@JsonPropertyOrder(value = {"id", "name", "email", "classRoomId", "address", "roles", "responsibilities"})
public class PrincipalDto extends UserDto { //DTO da classe Principal

    @JsonProperty(value = "responsibilities")
    private List<ResponsibilityDto> responsibilitiesDto = new ArrayList<>();

    public PrincipalDto(User user) {
        super(user);
        Principal principal = (Principal) user;
        this.responsibilitiesDto = convertList(principal.getResponsibilities());
    }

    private List<ResponsibilityDto> convertList(List<Responsibility> responsibilities) {
        return responsibilities.stream().map(ResponsibilityDto::new).collect(Collectors.toList());
    }

}
