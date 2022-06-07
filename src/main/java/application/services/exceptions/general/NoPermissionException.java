package application.services.exceptions.general;

import java.io.Serial;

public class NoPermissionException extends RuntimeException{ // Quando alguém não tem permissão para fazer algo.

    @Serial
    private static final long serialVersionUID = 1L;

    public NoPermissionException(String msg) {
        super(msg);
    }
}
