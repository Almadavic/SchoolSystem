package application.service.serviceLayer.interfacee;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AllUserTypeService<T> extends GenericMethodService {

    Page<T> findAll(Pageable pagination, String parameter);
}
