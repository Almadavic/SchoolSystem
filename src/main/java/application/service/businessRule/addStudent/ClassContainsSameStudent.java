package application.service.businessRule.addStudent;


import application.entity.ClassRoom;
import application.entity.user.Student;
import application.service.exception.classRoomService.ClassContainsSameStudentException;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(2)
public class ClassContainsSameStudent implements AddStudentCheck { // O aluno não pode já estar na própria sala
    @Override
    public void validation(Student student, ClassRoom classRoom) {
        if (classRoom.getStudents().stream().anyMatch(studentClass -> studentClass.equals(student))) {
            throw new ClassContainsSameStudentException("This student already is in this classroom");
        }
    }

}
