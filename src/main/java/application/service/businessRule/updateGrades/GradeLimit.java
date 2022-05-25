package application.service.businessRule.updateGrades;

import application.form.NewGradesForm;
import application.service.exception.classRoomService.GradeValueNotAllowed;

import java.util.Arrays;
import java.util.List;

public class GradeLimit implements UpdateCheck {

// Essa regra de négocio já foi implementada pelo proprio BeanValidation, onde não vai deixar as notas serem menor q 0 e maior q 10, mas reforçei nessa classe caso esqueça;
    @Override
    public void validation(NewGradesForm newGrades, String teacherName, String userLoggedName) { // As notas não podem ser menores que 0 e nem maiores que 10
        teacherName = null; //  Nullos porquê não tem função nessa classe.
        userLoggedName = null; //  Nullos porquê não tem função nessa classe.

        List<Double> grades = Arrays.asList(newGrades.getGrade1(), newGrades.getGrade2(), newGrades.getGrade3());
        double gradeLimitMax = 10;
        double gradLimitMin = 0;

        for (Double grade : grades) {
            if (grade > gradeLimitMax || grade<gradLimitMin) {
                throw new GradeValueNotAllowed("Grades is supposed to be between 0 and 10");
            }
        }

    }
}
