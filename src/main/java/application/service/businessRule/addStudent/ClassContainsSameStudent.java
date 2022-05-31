package application.service.businessRule.addStudent;


import application.entity.ClassRoom;
import application.entity.users.Student;
import application.service.exception.classRoomService.ClassContainsSameStudentException;

public class ClassContainsSameStudent implements AddStudentCheck{
    @Override
    public void validation(Student student, ClassRoom classRoom) { // O aluno não pode já estar na própria sala
      if (classRoom.getStudents().stream().anyMatch(studentClass -> studentClass.equals(student))) {
       throw new ClassContainsSameStudentException("This student already is in this classroom");
      }
    }

}
