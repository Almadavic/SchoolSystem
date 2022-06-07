package application.services.exceptions.classRoomService;

import java.io.Serial;

public class FullListException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public FullListException(String msg) {
        super(msg);
    }
}
