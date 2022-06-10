package application.controllers.controllerLayer.interfaces;

import application.dtos.UserDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AllUserTypeController<T> {  // Interface que contém os métodos aonde todas as classes TeacherController, UserController e StudentController vão implementar !
    ResponseEntity<List<? extends UserDto>> findAll(String param);

    T findById(Long id);

}
