package application.service.businessRule.updateGrade;

import application.domain.entity.ClassRoom;
import application.dto.request.NewGradesForm;
import application.service.exception.classRoomService.ThereIsntTeacherInThisClassException;
import application.service.exception.general.NoPermissionException;
import org.springframework.stereotype.Component;

import java.security.Principal;

@Component
public class TeacherAllowed implements UpdateGradesCheck {
    @Override
    public void validation(NewGradesForm newGrades, ClassRoom classRoom, Principal user) { // O professor que for alterar a nota do aluno tem q ser o mesmo professor da classe.
        newGrades = null;     //  Nullos porquê não tem função nessa classe.

        String userLoggedName = user.getName();
        String classTeacherName;


        if(classRoom.getTeacher() == null) {
            throw new ThereIsntTeacherInThisClassException("This class doesn't have any teacher");
        }
        classTeacherName = classRoom.getTeacher().getEmail();

        if (!classTeacherName.equals(userLoggedName)) {
            throw new NoPermissionException("Permission required to access this! Just the class's Teacher can access this!");
        }

    }
}
