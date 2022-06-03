package application.service.serviceLayer.interfacee;

import application.dto.UserDto;
import application.entity.users.User;



import java.util.List;

public interface AllUserTypeService<T>  {

    List<UserDto> findAll(String parameter);

    List<UserDto> verifyParameters(String parameter);

    User returnUser(Long id);

    T findById(Long id);

}
