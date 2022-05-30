package application.service.businessRule.addStudent;


import application.entity.ClassRoom;
import application.entity.users.Student;

public class ClassContainsSameStudent implements AddStudentCheck {
    @Override
    public void validation(Student student, ClassRoom classRoom) { // O aluno não pode já estar na própria sala
     //  throw  classRoom.getStudents().stream().filter(studentClass -> student.equals(studentClass))                    // NÃO FUNCIONAL!! ARUMAR UM JEITO !
       // .map(ResourceNotFoundException::new).iterator().next();
    }

}
