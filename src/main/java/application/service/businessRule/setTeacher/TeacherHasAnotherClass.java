package application.service.businessRule.setTeacher;

import application.entity.users.Teacher;
import application.service.exception.classRoomService.TeacherBelongsAnotherClass;

public class TeacherHasAnotherClass implements SetTeacherCheck {

    @Override
    public void validation(Teacher teacher) { // Se o aluno já estiver em uma classe, não pode estar em outra.
        if(teacher.getClassRoom()!=null) {
            throw new TeacherBelongsAnotherClass("The teacher already belongs an another class.");
        }
    }
}
