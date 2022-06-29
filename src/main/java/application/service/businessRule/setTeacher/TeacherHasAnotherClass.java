package application.service.businessRule.setTeacher;

import application.entity.user.Teacher;
import application.service.exception.classRoomService.TeacherHasAnotherClassException;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(2)
public class TeacherHasAnotherClass implements SetTeacherCheck { // Se o professor já estiver em uma classe, não pode estar em outra.

    @Override
    public void validation(Teacher teacher, Teacher classTeacher) {
        classTeacher = null; // null

        if (teacher.getClassRoom() != null) {
            throw new TeacherHasAnotherClassException("The teacher already belongs an another class.");
        }
    }
}
