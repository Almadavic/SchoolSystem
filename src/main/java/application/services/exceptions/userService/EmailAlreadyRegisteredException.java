package application.services.exceptions.userService;

import java.io.Serial;

public class EmailAlreadyRegisteredException extends RuntimeException{

    @Serial
    private static final long serialVersionUID = 1L;

    public EmailAlreadyRegisteredException(String msg) {
        super(msg);
    }
}
