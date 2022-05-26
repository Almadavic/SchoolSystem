package application.service.exception.classRoomService;

import java.io.Serial;

public class ThereIsntTeacherInThisClass extends RuntimeException{ // Quando não tem nenhum professor na sala.

    @Serial
    private static final long serialVersionUID = 1L;

    public ThereIsntTeacherInThisClass(String msg) {
        super(msg);
    }
}
