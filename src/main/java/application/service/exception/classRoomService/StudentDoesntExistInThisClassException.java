package application.service.exception.classRoomService;

import java.io.Serial;

public class StudentDoesntExistInThisClassException extends RuntimeException { // Quando não existe o estudante na classe.

    @Serial
    private static final long serialVersionUID = 1L;

    public StudentDoesntExistInThisClassException(String msg) {
        super(msg);
    }
}
