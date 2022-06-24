package application.service.exception.userService;

import java.io.Serial;

public class RemovePrincipalException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public RemovePrincipalException(String msg) {
        super(msg);
    }
}
