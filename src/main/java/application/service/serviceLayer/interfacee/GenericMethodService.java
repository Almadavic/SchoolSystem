package application.service.serviceLayer.interfacee;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GenericMethodService<T> { // Interface onde muitas classes no pacote controller vão implementar!

  T findById(Long id);



}
