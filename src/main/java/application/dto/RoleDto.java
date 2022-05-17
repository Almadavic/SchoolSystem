package application.dto;

import application.entity.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


@JsonPropertyOrder({"name"})
public class RoleDto {

    @JsonProperty("name")
    private String name;

    public RoleDto() {

    }

    public RoleDto(Role role) {
        this.name = role.getName();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
