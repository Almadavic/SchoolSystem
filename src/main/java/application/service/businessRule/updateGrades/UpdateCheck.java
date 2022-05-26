package application.service.businessRule.updateGrades;

import application.entity.ClassRoom;
import application.form.NewGradesForm;

import java.security.Principal;

public interface UpdateCheck { // Regras de négocio relacionado ao setar/mudar uma nota de algum aluno.

    void validation(NewGradesForm newGrades, ClassRoom classRoom, Principal user);  // Validação
}
