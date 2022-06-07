package application.controllers.controllerLayer.interfaces;

import application.dtos.UserDto;
import application.forms.RegisterUserForm;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

public interface ExtendsUserController extends AllUserTypeController { // Apenas as classes que extendes UserDto vaõ implementar essa interface!
    ResponseEntity<? extends UserDto> save(RegisterUserForm userForm, UriComponentsBuilder uriBuilder);

}
