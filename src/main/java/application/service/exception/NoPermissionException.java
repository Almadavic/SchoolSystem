package application.service.exception;

import java.io.Serial;

public class NoPermissionException extends RuntimeException{

    @Serial
    private static final long serialVersionUID = 1L;

    public NoPermissionException(String msg) {
        super(msg);
    }
}
