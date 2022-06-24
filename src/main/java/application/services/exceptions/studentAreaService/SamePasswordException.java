package application.services.exceptions.studentAreaService;

import java.io.Serial;

public class SamePasswordException extends RuntimeException { // Quando o usuário coloca a mesma senha do que a senha anterior.

    @Serial
    private static final long serialVersionUID = 1L;

    public SamePasswordException(String msg) {
        super(msg);
    }
}
