package application.service.businessRule.addStudent;

import application.entity.ClassRoom;
import application.entity.user.Student;
import application.service.exception.classRoomService.StudentHasAnotherClassException;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(3)
public class StudentHasAnotherClass implements AddStudentCheck {


    @Override
    public void validation(Student student, ClassRoom classRoom) { // Se o aluno já estiver em uma classe, não pode estar em outra.
        classRoom = null; // Só para deixar claro que não tem uso aqui.

        if (student.getClassRoom() != null) {
            throw new StudentHasAnotherClassException("The student already has an another class.");
        }
    }


}
