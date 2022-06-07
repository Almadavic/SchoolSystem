package application.repositories;


import application.entities.ClassRoom;
import application.entities.enums.ClassShift;
import application.entities.users.Student;
import application.entities.users.Teacher;
import application.repositories.interfaces.GeneralExtendsRepositoryTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;


@SpringBootTest
public class ClassRoomRepositoryTest implements GeneralExtendsRepositoryTest {

    @Autowired
    private ClassRoomRepository classRepository;

    @Autowired
    private TeacherRepository teacherRepository; // Necessário para testar o update!

    @Autowired
    private StudentRepository studentRepository; // Necessário para testar o update!

    @Test
    public void save() {

        ClassRoom classRoom = new ClassRoom('B', ClassShift.AFTERNOON);
        classRoom = classRepository.save(classRoom);
        ClassRoom classRoomDataBase = classRepository.findById(classRoom.getId()).get();
        Assertions.assertEquals(classRoom.getId(), classRoomDataBase.getId());
        classRepository.delete(classRoomDataBase);
    }

    @Test
    public void updateClassRoom() {           // AINDA NÃO FUNCIONAL!

        Long idClass = 1l;
        ClassRoom classRoom = classRepository.findById(idClass).get();
        Assertions.assertEquals("raphael@gmail.com",classRoom.getTeacher().getEmail());
        List<Student> students = classRoom.getStudents();Assertions.assertEquals(3,classRoom.getStudents().size());

        Long idTeacher = 6l;
        Teacher newTeacher = teacherRepository.findById(idTeacher).get();
        newTeacher.setClassRoom(classRoom);
        teacherRepository.save(newTeacher);
        classRoom.setTeacher(newTeacher);

        Long idStudent = 4l;
        Student student = studentRepository.findById(idStudent).get();
        classRoom.addStudent(student);
        student.setClassRoom(classRoom);
        studentRepository.save(student);
        classRoom = classRepository.save(classRoom);

        Assertions.assertEquals("euni@gmail.com",classRoom.getTeacher().getEmail());
        Assertions.assertEquals(4,classRoom.getStudents().size());
        Assertions.assertEquals(student.getEmail(),classRoom.getStudents().get(classRoom.getStudents().size()-1).getEmail());
    }


}
