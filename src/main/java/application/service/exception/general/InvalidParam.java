package application.service.exception.general;

import java.io.Serial;

public class InvalidParam extends RuntimeException { // Quando o client passa um parametro inválido

    @Serial
    private static final long serialVersionUID = 1L;

    public InvalidParam(String msg) {
        super(msg);
    }
}
