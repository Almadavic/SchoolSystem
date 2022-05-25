package application.service.serviceLayer.interfacee;


import application.dto.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ExtendsUserService extends GenericMethodService {

   Page<? extends UserDto> findAll(Pageable pagination, String noClass);

}
