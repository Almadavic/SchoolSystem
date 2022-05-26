package application.service.exception.classRoomService;

import java.io.Serial;


public class GradeValueNotAllowed  extends RuntimeException { // Quando o client passa um valor inválido para uma nota.

    @Serial
    private static final long serialVersionUID = 1L;

    public GradeValueNotAllowed(String msg) {
        super(msg);
    }
}
