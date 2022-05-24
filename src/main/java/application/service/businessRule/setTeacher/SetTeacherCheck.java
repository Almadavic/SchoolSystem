package application.service.businessRule.setTeacher;

import application.entity.ClassRoom;
import application.entity.users.Teacher;

public interface SetTeacherCheck {

    void validation(Teacher teacher); // Regras de négocio relacionado ao adicionar o aluno em alguma sala.
}
