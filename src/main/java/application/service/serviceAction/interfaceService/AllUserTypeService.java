package application.service.serviceAction.interfaceService;

import application.dto.response.UserDto;
import application.entity.user.User;


import java.util.List;

public interface AllUserTypeService<T> { // Interface onde UserService, TeacherService e StudentService vão implementar!!

    List<UserDto> findAll(String parameter);

    List<UserDto> verifyParameters(String parameter);

    User returnUser(Long id);

    T findById(Long id);

}
