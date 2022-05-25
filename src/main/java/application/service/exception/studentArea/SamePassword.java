package application.service.exception.studentArea;

import java.io.Serial;

public class SamePassword extends RuntimeException{

    @Serial
    private static final long serialVersionUID = 1L;

    public SamePassword(String msg) {
        super(msg);
    }
}
