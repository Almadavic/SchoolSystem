package application.service.serviceLayer.interfacee;


public interface GenericMethodService<T> { // Interface onde muitas classes no pacote controller vão implementar!

  T findById(Long id);

}
