package application.controller.controllerLayer.interfacee;

import application.dto.UserDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AllUserTypeController extends GenericMethodController{
    ResponseEntity<List<? extends UserDto>> findAll(String param); // Interface onde todos os usuários vão importar esse método!

}
