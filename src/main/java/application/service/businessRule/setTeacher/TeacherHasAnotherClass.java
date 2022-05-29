package application.service.businessRule.setTeacher;

import application.entity.users.Teacher;
import application.service.exception.classRoomService.TeacherHasAnotherClassException;

public class TeacherHasAnotherClass implements SetTeacherCheck {

    @Override
    public void validation(Teacher teacher, Teacher classTeacher) { // Se o professor já estiver em uma classe, não pode estar em outra.
        classTeacher=null; // null

        if(teacher.getClassRoom()!=null) {
            throw new TeacherHasAnotherClassException("The teacher already belongs an another class.");
        }
    }
}
