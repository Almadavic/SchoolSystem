package application.services.businessRules.updateGrades;

import application.entities.ClassRoom;
import application.forms.NewGradesForm;
import application.services.exceptions.general.NoPermissionException;

import java.security.Principal;

public class TeacherAllowed implements UpdateGradesCheck {
    @Override
    public void validation(NewGradesForm newGrades, ClassRoom classRoom, Principal user) { // O professor que for alterar a nota do aluno tem q ser o mesmo professor da classe.
        newGrades = null;     //  Nullos porquê não tem função nessa classe.

        String userLoggedName = user.getName();
        String teacherClassName = classRoom.getTeacher().getEmail();

        if (!teacherClassName.equals(userLoggedName)) {
            throw new NoPermissionException("Permission required to access this! Just the class's Teacher can access this!");
        }

    }
}
