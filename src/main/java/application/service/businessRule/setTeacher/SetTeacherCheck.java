package application.service.businessRule.setTeacher;

import application.entity.ClassRoom;
import application.entity.users.Teacher;

public interface SetTeacherCheck { // Regras de négocio relacionado ao setar o professor em alguma sala.

    void validation(Teacher teacher,Teacher classTeacher); // Validação!
}
