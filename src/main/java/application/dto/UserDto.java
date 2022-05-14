package application.dto;

import application.entity.Role;
import application.entity.User;

import java.util.ArrayList;
import java.util.List;

public class UserDto {

    private Long id;

    private String name;

    private String email;

    private List<RoleDto> rolesDto = new ArrayList<>();

    public UserDto() {

    }

    public void convertList(List<Role> roles) {
         for(Role role : roles) {
             rolesDto.add(new RoleDto(role));
         }
    }

    public UserDto(User user) {
        this.email=user.getEmail();
        this.name=user.getName();
        this.id=user.getId();
        convertList(user.getAuthorities());                   // Método necessita de refatoração nessa parte
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

    public void setUserName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public List<RoleDto> getRoles() {
        return rolesDto;
    }
}
