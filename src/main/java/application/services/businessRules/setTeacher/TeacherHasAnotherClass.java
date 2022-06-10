package application.services.businessRules.setTeacher;

import application.entities.users.Teacher;
import application.services.exceptions.classRoomService.TeacherHasAnotherClassException;

public class TeacherHasAnotherClass implements SetTeacherCheck { // Se o professor já estiver em uma classe, não pode estar em outra.

    @Override
    public void validation(Teacher teacher, Teacher classTeacher) {
        classTeacher=null; // null

        if(teacher.getClassRoom()!=null) {
            throw new TeacherHasAnotherClassException("The teacher already belongs an another class.");
        }
    }
}
