package application.service.exception.classRoom.students;

import java.io.Serial;

public class StudentBelongsSameClass extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public StudentBelongsSameClass(String msg) {
        super(msg);
    }
}
