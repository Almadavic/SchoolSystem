package application.services.exceptions.general;

import java.io.Serial;

public class InvalidParamException extends RuntimeException { // Quando o client passa um parametro inválido

    @Serial
    private static final long serialVersionUID = 1L;

    public InvalidParamException(String msg) {
        super(msg);
    }
}
