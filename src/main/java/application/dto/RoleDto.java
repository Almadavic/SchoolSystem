package application.dto;

import application.entity.Role;

public class RoleDto {

    private String name;

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
