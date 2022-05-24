package application.service.businessRule.updateGrades;

import application.form.NewGradesForm;

public interface UpdateCheck {

    void validation(NewGradesForm newGrades,String teacherName,String userLoggedName); // Regras de négocio relacionado ao setar/mudar uma nota de algum aluno.
}
