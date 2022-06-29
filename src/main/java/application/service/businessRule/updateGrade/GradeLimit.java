package application.service.businessRule.updateGrade;

import application.entity.ClassRoom;
import application.dto.request.NewGradesForm;
import application.service.exception.classRoomService.GradeLimitException;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;

@Component
@Order(2)
public class GradeLimit implements UpdateGradesCheck {

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
                    throw new GradeLimitException("Grades is supposed to be between 0 and 10");
                }
            }
        }

    }
}
