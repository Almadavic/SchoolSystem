package application.dto;

import application.entity.Responsibility;
import application.entity.users.Principal;
import application.entity.users.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@JsonPropertyOrder(value = {"id","name","email","classRoomId","address","roles","responsibilities"})
public class PrincipalDto extends UserDto{

    @JsonProperty(value = "responsibilities")
    private List<ResponsibilityDto> responsibilitiesDto = new ArrayList<>();

    public PrincipalDto() {

    }
    public PrincipalDto(User user) {
        super(user);
        Principal principal = (Principal) user;
        this.responsibilitiesDto=convertList(principal.getResponsibilities());
    }

    private List<ResponsibilityDto> convertList(List<Responsibility> responsibilities) {
        return responsibilities.stream().map(ResponsibilityDto::new).collect(Collectors.toList());
    }

    public List<ResponsibilityDto> getResponsibilitiesDto() {
        return responsibilitiesDto;
    }

}
