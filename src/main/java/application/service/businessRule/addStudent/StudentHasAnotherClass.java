package application.service.businessRule.addStudent;

import application.domain.entity.ClassRoom;
import application.domain.entity.user.Student;
import application.service.exception.classRoomService.StudentHasAnotherClassException;
import org.springframework.stereotype.Component;

@Component
public class StudentHasAnotherClass implements AddStudentCheck {


    @Override
    public void validation(Student student, ClassRoom classRoom) { // Se o aluno já estiver em uma classe, não pode estar em outra.
        classRoom = null; // Só para deixar claro que não tem uso aqui.

        if (student.getClassRoom() != null) {
            throw new StudentHasAnotherClassException("The student already has an another class.");
        }
    }


}
