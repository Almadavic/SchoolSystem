package application.services.exceptions.classRoomService;

import java.io.Serial;

public class ClassContainsSameStudentException extends RuntimeException { // Quando o estudante já pertence a mesma classe.

    @Serial
    private static final long serialVersionUID = 1L;

    public ClassContainsSameStudentException(String msg) {
        super(msg);
    }
}
