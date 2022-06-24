package application.service.exception.studentAreaService;

import java.io.Serial;

public class ShortPasswordException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public ShortPasswordException(String msg) {
        super(msg);
    }
}
