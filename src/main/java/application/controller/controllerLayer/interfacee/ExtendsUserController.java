package application.controller.controllerLayer.interfacee;

import application.dto.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface ExtendsUserController extends GenericMethodController { // Apenas as classes que extendes User vaõ implementar essa interface!

    ResponseEntity<Page<? extends UserDto>> findAll(Pageable pagination, String noClass); // Arrumar método
}
