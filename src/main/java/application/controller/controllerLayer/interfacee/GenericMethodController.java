package application.controller.controllerLayer.interfacee;

import application.dto.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface GenericMethodController <T>{ // Interface onde muitas classes no pacote controller vão implementar!
    T findById(Long id);

}