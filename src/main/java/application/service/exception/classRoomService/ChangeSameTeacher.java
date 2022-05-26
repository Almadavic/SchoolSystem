package application.service.exception.classRoomService;

import java.io.Serial;

public class ChangeSameTeacher extends RuntimeException{

    @Serial
    private static final long serialVersionUID = 1L;

    public ChangeSameTeacher(String msg) {
        super(msg);
    }

}
