package application.businessRule.updateGrades;

import application.form.NewGradesForm;
import application.service.exception.NoPermissionException;

public class TeacherAllowed implements UpdateCheck {
    @Override
    public void validation(NewGradesForm newGrades, String teacherName, String userLoggedName) {
        newGrades = null;

        if (!teacherName.equals(userLoggedName)) {
            throw new NoPermissionException("Permission required to access this! Teacher with no permission");
        }

    }
}
