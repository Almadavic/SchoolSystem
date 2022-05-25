package application.service.businessRule.addStudent;


import application.entity.ClassRoom;
import application.entity.users.Student;
import application.service.exception.classRoomService.StudentBelongsSameClass;

public class ClassContainsSameStudent implements AddStudentCheck {


    @Override
    public void validation(Student student, ClassRoom classRoom) { // O aluno não pode já estar na própria sala

        boolean classRoomContainsNewStudent = checkIfStudentExistsInTheClassRoom(student, classRoom);

        if (classRoomContainsNewStudent) {
            throw new StudentBelongsSameClass("This class already contains this student");
        }
    }

    private boolean checkIfStudentExistsInTheClassRoom(Student student, ClassRoom classRoom) { // devolve V ou F se o aluno já está presente nessa sala.

        for (Student studentClass : classRoom.getStudents()) {
            if (studentClass.equals(student)) {
                return true;
            }
        }
        return false;
    }
}
