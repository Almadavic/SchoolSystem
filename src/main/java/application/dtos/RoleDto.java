package application.dtos;

import application.entities.Role;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonPropertyOrder(value = {"name"})
public class RoleDto { // Dto da classe Role
    @JsonProperty(value = "name")
    private String name;

    public RoleDto(Role role) {     // Transformando um Role em RoleDto
        this.name = role.getName();
    }
}
