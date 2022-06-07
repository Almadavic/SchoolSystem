package application.services.serviceLayer.interfaces;

import application.dtos.UserDto;
import application.entities.users.User;



import java.util.List;

public interface AllUserTypeService<T>  {

    List<UserDto> findAll(String parameter);

    List<UserDto> verifyParameters(String parameter);

    User returnUser(Long id);

    T findById(Long id);

}
