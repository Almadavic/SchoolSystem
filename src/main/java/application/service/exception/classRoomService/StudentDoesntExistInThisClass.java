package application.service.exception.classRoomService;

import java.io.Serial;

public class StudentDoesntExistInThisClass extends RuntimeException { // Quando não existe o estudante na classe.

    @Serial
    private static final long serialVersionUID = 1L;

    public StudentDoesntExistInThisClass(String msg) {
        super(msg);
    }
}
