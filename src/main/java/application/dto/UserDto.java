package application.dto;

import application.entity.Role;
import application.entity.users.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.ArrayList;
import java.util.List;

@JsonPropertyOrder({"id", "name", "email", "address", "roles"})
public class UserDto {

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


    public UserDto(User user) {
        this.email = user.getEmail();
        this.name = user.getName();
        this.id = user.getId();
        this.addressDto = new AddressDto(user.getAddress());
        convertList(user.getAuthorities());
    }

    public void convertList(List<Role> roles) {
        for (Role role : roles) {
            rolesDto.add(new RoleDto(role));     // Método necessita de refatoração nessa parte para stream
        }
    }
    public void addRolesDto(RoleDto roleDto) {
        rolesDto.add(roleDto);
    }

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
