package application.service.businessRule.addStudent;

import application.entity.ClassRoom;
import application.entity.users.Student;

public interface AddStudentCheck {

    void validation(Student student, ClassRoom classRoom); // Regras de négocio relacionado ao adicionar o aluno em alguma sala.
}
