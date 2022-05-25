package application.service.exception.classRoomService;

import java.io.Serial;


public class GradeValueNotAllowed  extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public GradeValueNotAllowed(String msg) {
        super(msg);
    }
}
