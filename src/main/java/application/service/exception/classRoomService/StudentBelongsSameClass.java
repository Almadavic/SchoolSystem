package application.service.exception.classRoomService;

import java.io.Serial;

public class StudentBelongsSameClass extends RuntimeException { // Quando o estudante já pertence a mesma classe.

    @Serial
    private static final long serialVersionUID = 1L;

    public StudentBelongsSameClass(String msg) {
        super(msg);
    }
}
