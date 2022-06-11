package application.services.exceptions.classRoomService;

import java.io.Serial;

public class MaximumOfClassRoomsException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public MaximumOfClassRoomsException(String msg) {
        super(msg);
    }
}
