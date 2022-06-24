package application.service.businessRule.updateGrade;

import application.domain.entity.ClassRoom;
import application.dto.request.NewGradesForm;

import java.security.Principal;

public interface UpdateGradesCheck { // Regras de négocio relacionado ao setar/mudar uma nota de algum aluno.

    void validation(NewGradesForm newGrades, ClassRoom classRoom, Principal user);  // Validação
}
