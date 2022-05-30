package application.dto;

import application.entity.Role;
import application.entity.users.Student;
import application.entity.users.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@JsonPropertyOrder({"id", "name", "email", "address", "roles"})
public class UserDto { // Dto da classe User

    @JsonProperty("id")
    private Long id;
    @JsonProperty("name")
    private String name;

    @JsonProperty("email")
    private String email;

    @JsonProperty("address")
    private AddressDto addressDto;

    @JsonProperty("roles")
    private List<RoleDto> rolesDto = new ArrayList<>();


    public UserDto() {

    }


    public UserDto(User user) { // Método que transforma um User em UserDto
        this.email = user.getEmail();
        this.name = user.getName();
        this.id = user.getId();
        this.addressDto = new AddressDto(user.getAddress());
        this.rolesDto = convertList(user.getAuthorities());
    }

    private List<RoleDto> convertList(List<Role> roles) {                    // Método converte uma lista de Role para RolesDto
        return roles.stream().map(RoleDto::new).collect(Collectors.toList());
    }

    public void addRoleDto(RoleDto roleDto) {
        rolesDto.add(roleDto);
    } // Adiciona uma Role

    public void removeRoleDto(Long id) { rolesDto.remove(id);} // Remove uma Role

}
