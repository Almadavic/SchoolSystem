package application.businessRule.updateGrades;

import application.form.NewGradesForm;

public interface UpdateCheck {

    void validation(NewGradesForm newGrades,String teacherName,String userLoggedName);
}
