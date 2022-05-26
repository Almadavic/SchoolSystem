package application.service.exception.classRoomService;

import java.io.Serial;

public class StudentBelongsAnotherClass extends RuntimeException { // Quando o estudante pertence á uma outra classe

    @Serial
    private static final long serialVersionUID = 1L;

    public StudentBelongsAnotherClass(String msg) {
        super(msg);
    }
}
