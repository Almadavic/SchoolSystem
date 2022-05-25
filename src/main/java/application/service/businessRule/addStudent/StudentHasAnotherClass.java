package application.service.businessRule.addStudent;

import application.entity.ClassRoom;
import application.entity.users.Student;
import application.service.exception.classRoomService.StudentBelongsAnotherClass;

public class StudentHasAnotherClass implements AddStudentCheck {


    @Override
    public void validation(Student student, ClassRoom classRoom) { // Se o aluno já estiver em uma classe, não pode estar em outra.
        classRoom=null; // Só para deixar claro que não tem uso.
         if(student.getClassRoom()!=null) {
           throw new StudentBelongsAnotherClass("The student already has an another class.");
         }
    }


}
