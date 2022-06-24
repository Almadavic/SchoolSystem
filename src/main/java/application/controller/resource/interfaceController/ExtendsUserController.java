package application.controller.resource.interfaceController;

import application.dto.response.UserDto;
import application.dto.request.RegisterUserForm;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

public interface ExtendsUserController extends AllUserTypeController { // Interface que contém os métodos onde TeacherController e StudentController vão implementar.
    ResponseEntity<? extends UserDto> save(RegisterUserForm userForm, UriComponentsBuilder uriBuilder);

}
