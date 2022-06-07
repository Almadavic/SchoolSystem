package application.services.exceptions.classRoomService;

import java.io.Serial;

public class StudentHasAnotherClassException extends RuntimeException { // Quando o estudante pertence á uma outra classe

    @Serial
    private static final long serialVersionUID = 1L;

    public StudentHasAnotherClassException(String msg) {
        super(msg);
    }
}
