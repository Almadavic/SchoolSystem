package application.controller.resource.interfaceController;

import application.dto.response.UserDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AllUserTypeController<T> {  // Interface que contém os métodos aonde todas as classes TeacherController, UserController e StudentController vão implementar !

    ResponseEntity<List<? extends UserDto>> findAll(String param);

    T findById(Long id);

}
