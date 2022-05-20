package application.businessRule.updateGrades;

import application.form.NewGradesForm;
import application.service.exception.GradeValueNotAllowed;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GradeLimit implements UpdateCheck {


    @Override
    public void validation(NewGradesForm newGrades, String teacherName, String userLoggedName) {
        teacherName = null;
        userLoggedName = null;

        List<Double> grades = Arrays.asList(newGrades.getGrade1(), newGrades.getGrade2(), newGrades.getGrade3());
        double gradeLimit = 10;

        for (Double number : grades) {
            if (number > gradeLimit) {
                throw new GradeValueNotAllowed("Grades is supposed to be between 0 and 10");
            }
        }

    }
}
