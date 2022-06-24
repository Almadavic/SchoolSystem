package application.service.exception.classRoomService;

import java.io.Serial;

public class TeacherDoesntHaveAnyClassException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public TeacherDoesntHaveAnyClassException(String msg) {
        super(msg);
    }
}
