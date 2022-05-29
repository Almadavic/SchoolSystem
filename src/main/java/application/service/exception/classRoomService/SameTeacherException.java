package application.service.exception.classRoomService;

import java.io.Serial;

public class SameTeacherException extends RuntimeException{

    @Serial
    private static final long serialVersionUID = 1L;

    public SameTeacherException(String msg) {
        super(msg);
    }

}
