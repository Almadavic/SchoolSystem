package application.service.exception.classRoomService;

import java.io.Serial;

public class TeacherDoesntHaveAnyClass extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public TeacherDoesntHaveAnyClass(String msg) {
        super(msg);
    }
}
