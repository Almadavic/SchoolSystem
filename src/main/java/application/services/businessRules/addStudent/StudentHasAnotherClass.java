package application.services.businessRules.addStudent;

import application.entities.ClassRoom;
import application.entities.users.Student;
import application.services.exceptions.classRoomService.StudentHasAnotherClassException;

public class StudentHasAnotherClass implements AddStudentCheck {


    @Override
    public void validation(Student student, ClassRoom classRoom) { // Se o aluno já estiver em uma classe, não pode estar em outra.
        classRoom=null; // Só para deixar claro que não tem uso aqui.
         if(student.getClassRoom()!=null) {
           throw new StudentHasAnotherClassException("The student already has an another class.");
         }
    }


}
