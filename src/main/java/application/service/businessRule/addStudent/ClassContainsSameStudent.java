package application.service.businessRule.addStudent;


import application.domain.entity.ClassRoom;
import application.domain.entity.user.Student;
import application.service.exception.classRoomService.ClassContainsSameStudentException;
import org.springframework.stereotype.Component;

@Component
public class ClassContainsSameStudent implements AddStudentCheck { // O aluno não pode já estar na própria sala
    @Override
    public void validation(Student student, ClassRoom classRoom) {
        if (classRoom.getStudents().stream().anyMatch(studentClass -> studentClass.equals(student))) {
            throw new ClassContainsSameStudentException("This student already is in this classroom");
        }
    }

}
