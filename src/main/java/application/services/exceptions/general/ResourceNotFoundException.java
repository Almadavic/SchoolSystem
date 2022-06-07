package application.services.exceptions.general;

import java.io.Serial;

public class ResourceNotFoundException extends RuntimeException { // Quando o recurso não é encontrado no banco de dados.

    @Serial
    private static final long serialVersionUID = 1L;
	
      public ResourceNotFoundException(Object id) {
              super(""+id);

      }
}
