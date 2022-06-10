package application.services.businessRules.addStudent;

import application.entities.ClassRoom;
import application.entities.users.Student;

public interface AddStudentCheck { // Interface de regras de negócio relacionada ao adicionar o aluno em alguma sala

    void validation(Student student, ClassRoom classRoom); // Validação!
}
