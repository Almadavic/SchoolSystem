package application.service.exception;

import java.io.Serial;

public class StudentAlreadyExists extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public StudentAlreadyExists(String msg) {
        super(msg);
    }
}
