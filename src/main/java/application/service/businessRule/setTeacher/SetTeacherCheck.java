package application.service.businessRule.setTeacher;

import application.domain.entity.user.Teacher;

public interface SetTeacherCheck { // Regras de negócio relacionado ao setar o professor em alguma sala.

    void validation(Teacher teacher, Teacher classTeacher); // Validação!
}
