package application.controllers.controllerLayer.interfaces;

import application.dtos.UserDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AllUserTypeController<T> {
    ResponseEntity<List<? extends UserDto>> findAll(String param); // Interface onde todos os usuários vão importar esse método!

    T findById(Long id);

}
