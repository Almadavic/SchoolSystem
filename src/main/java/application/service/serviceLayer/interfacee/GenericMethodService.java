package application.service.serviceLayer.interfacee;

public interface GenericMethodService<T> {

  T findById(Long id);

}
