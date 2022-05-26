package application.service.businessRule.updateGrades;

import application.entity.ClassRoom;
import application.form.NewGradesForm;
import application.service.exception.classRoomService.GradeValueNotAllowed;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;

public class GradeLimit implements UpdateCheck {

    @Override
    public void validation(NewGradesForm newGrades, ClassRoom classRoom, Principal user) { // As notas não podem ser menores que 0 e nem maiores que 10
        classRoom = null;//  Nullos porquê não tem função nessa classe.
        user = null; //  Nullos porquê não tem função nessa classe.

        List<Double> grades = Arrays.asList(newGrades.getGrade1(), newGrades.getGrade2(), newGrades.getGrade3());

        double gradeLimitMax = 10;
        double gradLimitMin = 0;

        for (Double grade : grades) {
            if (grade != null) {
                if (grade > gradeLimitMax || grade < gradLimitMin) {
                    throw new GradeValueNotAllowed("Grades is supposed to be between 0 and 10");      // Esse método precisa de melhoria, quando envia a requisição
                }                                                                                         // com um dos campos errados, o programa não dá erro.
            }
        }

    }
}
