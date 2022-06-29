package application.service.businessRule.addStudent;

import application.entity.ClassRoom;
import application.entity.user.Student;

public interface AddStudentCheck { // Interface de regras de negócio relacionada ao adicionar o aluno em alguma sala

    void validation(Student student, ClassRoom classRoom); // Validação!
}
