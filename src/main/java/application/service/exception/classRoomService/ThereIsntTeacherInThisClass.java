package application.service.exception.classRoomService;

import java.io.Serial;

public class ThereIsntTeacherInThisClass extends RuntimeException{

    @Serial
    private static final long serialVersionUID = 1L;

    public ThereIsntTeacherInThisClass(String msg) {
        super(msg);
    }
}
