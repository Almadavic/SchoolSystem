package application.dto;

import application.entity.users.User;

public class PrincipalDto extends UserDto{

    public PrincipalDto() {

    }
    public PrincipalDto(User user) {
        super(user);
    }

}
