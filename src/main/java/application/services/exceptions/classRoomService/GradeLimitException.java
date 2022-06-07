package application.services.exceptions.classRoomService;

import java.io.Serial;


public class GradeLimitException extends RuntimeException { // Quando o client passa um valor inválido para uma nota.

    @Serial
    private static final long serialVersionUID = 1L;

    public GradeLimitException(String msg) {
        super(msg);
    }
}
