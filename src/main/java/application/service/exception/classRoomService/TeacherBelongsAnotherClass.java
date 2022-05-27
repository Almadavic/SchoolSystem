package application.service.exception.classRoomService;

import java.io.Serial;

public class TeacherBelongsAnotherClass extends RuntimeException { // Quando o professor já está associada á outra classe

    @Serial
    private static final long serialVersionUID = 1L;

    public TeacherBelongsAnotherClass(String msg) {
        super(msg);
    }
}