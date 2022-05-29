package application.service.exception.classRoomService;

import java.io.Serial;

public class TeacherHasAnotherClassException extends RuntimeException { // Quando o professor já está associada á outra classe

    @Serial
    private static final long serialVersionUID = 1L;

    public TeacherHasAnotherClassException(String msg) {
        super(msg);
    }
}
