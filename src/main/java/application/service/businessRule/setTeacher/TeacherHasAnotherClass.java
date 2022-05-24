package application.service.businessRule.setTeacher;

import application.entity.ClassRoom;
import application.entity.users.Student;
import application.entity.users.Teacher;
import application.service.exception.classRoom.students.StudentBelongsAnotherClass;
import application.service.exception.classRoom.teachers.TeacherBelongsAnotherClass;

public class TeacherHasAnotherClass implements SetTeacherCheck {

    @Override
    public void validation(Teacher teacher) { // Se o aluno já estiver em uma classe, não pode estar em outra.
        if(teacher.getClassRoom()!=null) {
            throw new TeacherBelongsAnotherClass("The teacher already belongs an another class.");
        }
    }
}
