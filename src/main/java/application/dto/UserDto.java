package application.dto;

import application.entity.Role;
import application.entity.users.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.ArrayList;
import java.util.List;

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
        convertList(user.getAuthorities());
    }

    protected void convertList(List<Role> roles) { // Método converte uma lista de Role para RolesDto
        for (Role role : roles) {
            rolesDto.add(new RoleDto(role));     // Método necessita de refatoração nessa parte para stream
        }
    }

    public void addRoleDto(RoleDto roleDto) {
        rolesDto.add(roleDto);
    } // Adiciona uma Role

    public void removeRoleDto(Long id) { rolesDto.remove(id);} // Remove uma Role

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<RoleDto> getRolesDto() {
        return rolesDto;
    }

    public AddressDto getAddressDto() {
        return addressDto;
    }

    public void setAddressDto(AddressDto addressDto) {
        this.addressDto = addressDto;
    }
}
