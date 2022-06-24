package application.service.businessRule.addStudent;

import application.domain.entity.ClassRoom;
import application.domain.entity.user.Student;
import application.service.exception.classRoomService.FullListException;
import org.springframework.stereotype.Component;

@Component
public class FullList implements AddStudentCheck { // Se a turma estiver cheia, um novo aluno não deve ser adicionado!

    @Override
    public void validation(Student student, ClassRoom classRoom) {
        student = null;      // Só para deixar claro que não tem uso!

        int numberStudents = classRoom.getStudents().size();
        if (numberStudents >= 10) {
            throw new FullListException("The list of students in a classroom cannot be bigger than 10");
        }
    }
}
