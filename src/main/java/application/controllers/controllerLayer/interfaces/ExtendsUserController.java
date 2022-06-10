package application.controllers.controllerLayer.interfaces;

import application.dtos.UserDto;
import application.forms.RegisterUserForm;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

public interface ExtendsUserController extends AllUserTypeController { // Interface que contém os métodos onde TeacherController e StudentController vão implementar.
    ResponseEntity<? extends UserDto> save(RegisterUserForm userForm, UriComponentsBuilder uriBuilder);

}
