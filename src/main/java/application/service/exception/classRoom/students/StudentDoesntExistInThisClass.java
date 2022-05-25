package application.service.exception.classRoom.students;

import java.io.Serial;

public class StudentDoesntExistInThisClass extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public StudentDoesntExistInThisClass(String msg) {
        super(msg);
    }
}
