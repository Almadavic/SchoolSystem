package application.businessRule.updateGrades;

import application.form.NewGradesForm;
import application.service.exception.NoPermissionException;

public class TeacherAllowed implements UpdateCheck {
    @Override
    public void validation(NewGradesForm newGrades, String teacherName, String userLoggedName) { // O professor que for alterar a nota do aluno tem q ser o mesmo professor da classe.
        newGrades = null;     //  Nullos porquê não tem função nessa classe.

        if (!teacherName.equals(userLoggedName)) {
            throw new NoPermissionException("Permission required to access this! Teacher with no permission");
        }

    }
}
