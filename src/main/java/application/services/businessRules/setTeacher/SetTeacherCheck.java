package application.services.businessRules.setTeacher;

import application.entities.users.Teacher;

public interface SetTeacherCheck { // Regras de negócio relacionado ao setar o professor em alguma sala.

    void validation(Teacher teacher, Teacher classTeacher); // Validação!
}
