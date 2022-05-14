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


    public UserDto(User user) {
        this.email=user.getEmail();
        this.name=user.getName();
        this.id=user.getId();
        for(int i=0;i<user.getAuthorities().size();i++) {
            rolesDto.add(new RoleDto(user.getAuthorities().get(i)));                // Método necessita de refatoração nessa parte
        }
    }

    public Long getId() {
        return id;
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
