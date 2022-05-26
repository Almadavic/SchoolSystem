package application.controller.controllerLayer.interfacee;

import application.dto.UserDto;
import application.form.RegisterUserForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

public interface ExtendsUserController extends AllUserTypeController { // Apenas as classes que extendes UserDto vaõ implementar essa interface!
    ResponseEntity<? extends UserDto> save(RegisterUserForm userForm, UriComponentsBuilder uriBuilder);

}
