package application.services.businessRules.updateGrades;

import application.entities.ClassRoom;
import application.forms.NewGradesForm;

import java.security.Principal;

public interface UpdateCheck { // Regras de négocio relacionado ao setar/mudar uma nota de algum aluno.

    void validation(NewGradesForm newGrades, ClassRoom classRoom, Principal user);  // Validação
}
