package application.dto;

import application.entity.Registration;
import application.entity.Role;
import application.entity.users.Student;
import application.entity.users.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@JsonPropertyOrder(value = {"id", "name", "email", "address", "roles", "registration"})
public class UserDto { // Dto da classe User

    @JsonProperty(value = "id")
    private Long id;
    @JsonProperty(value = "name")
    private String name;

    @JsonProperty(value = "email")
    private String email;

    @JsonProperty(value = "address")
    private AddressDto addressDto;

    @JsonProperty(value = "roles")
    private List<RoleDto> rolesDto = new ArrayList<>();

    @JsonProperty(value = "registration")
    private RegistrationDto registrationDto;


    public UserDto(User user) { // Método que transforma um User em UserDto
        this.email = user.getEmail();
        this.name = user.getName();
        this.id = user.getId();
        this.addressDto = new AddressDto(user.getAddress());
        this.registrationDto = new RegistrationDto(user.getRegistration());
        this.rolesDto = convertList(user.getAuthorities());
    }

    private List<RoleDto> convertList(List<Role> roles) {                    // Método converte uma lista de Role para RolesDto
        return roles.stream().map(RoleDto::new).collect(Collectors.toList());
    }

}
