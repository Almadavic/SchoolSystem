package application.controller.controllerLayer.interfacee;

public interface GenericMethodController <T>{ // Interface onde muitas classes no pacote controller vão implementar!
    T findById(Long id);

}
