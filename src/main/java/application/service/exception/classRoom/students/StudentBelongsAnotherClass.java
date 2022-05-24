package application.service.exception.classRoom.students;

import java.io.Serial;

public class StudentBelongsAnotherClass extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public StudentBelongsAnotherClass(String msg) {
        super(msg);
    }
}
