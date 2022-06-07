package application.services.businessRules.addStudent;


import application.entities.ClassRoom;
import application.entities.users.Student;
import application.services.exceptions.classRoomService.ClassContainsSameStudentException;

public class ClassContainsSameStudent implements AddStudentCheck{
    @Override
    public void validation(Student student, ClassRoom classRoom) { // O aluno não pode já estar na própria sala
      if (classRoom.getStudents().stream().anyMatch(studentClass -> studentClass.equals(student))) {
       throw new ClassContainsSameStudentException("This student already is in this classroom");
      }
    }

}