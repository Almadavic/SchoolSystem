package application.dto;

import application.entity.Role;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonPropertyOrder({"name"})
public class RoleDto { // Dto da classe Role

    @JsonProperty("name")
    private String name;

    public RoleDto(Role role) {     // Transformando um Role em RoleDto
        this.name = role.getName();
    }
}
