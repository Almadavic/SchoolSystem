package application.services.exceptions.classRoomService;

import java.io.Serial;

public class ThereIsntTeacherInThisClassException extends RuntimeException{ // Quando não tem nenhum professor na sala.

    @Serial
    private static final long serialVersionUID = 1L;

    public ThereIsntTeacherInThisClassException(String msg) {
        super(msg);
    }
}
