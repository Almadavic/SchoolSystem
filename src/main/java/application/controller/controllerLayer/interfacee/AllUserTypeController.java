package application.controller.controllerLayer.interfacee;

import application.dto.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface AllUserTypeController extends GenericMethodController{
    ResponseEntity<Page<? extends UserDto>> findAll(Pageable pagination, String noClass);

}
