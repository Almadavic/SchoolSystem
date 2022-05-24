package application.service.exception.classRoom.teachers;

import java.io.Serial;

public class TeacherBelongsAnotherClass extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public TeacherBelongsAnotherClass(String msg) {
        super(msg);
    }
}
