package application.integrationTests.repositories;


import application.entities.ClassRoom;
import application.entities.enums.ClassShift;
import application.entities.users.Student;
import application.entities.users.Teacher;
import application.integrationTests.repositories.interfaces.GeneralExtendsRepositoryTest;
import application.repositories.ClassRoomRepository;
import application.repositories.StudentRepository;
import application.repositories.TeacherRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;


@SpringBootTest
@Profile(value = "test")
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
        Assertions.assertEquals("raphael@gmail.com", classRoom.getTeacher().getEmail());
        Assertions.assertEquals(3,classRoom.getStudents().size());          // problema!
        Teacher teacherClass = classRoom.getTeacher();
        removeTeacher(teacherClass, classRoom);
        Long idTeacher = 7l;
        Teacher teacherDataBase = teacherRepository.findById(idTeacher).get();
        setTeacher(teacherDataBase, classRoom);

        ClassRoom classRoomDataBase = classRepository.findById(idClass).get();
        Assertions.assertEquals("euni@gmail.com", classRoomDataBase.getTeacher().getEmail());
    }

    private void removeTeacher(Teacher teacherClass, ClassRoom classRoom) {
        teacherClass.setClassRoom(null);
        classRoom.setTeacher(null);
        teacherRepository.save(teacherClass);
    }

    private void setTeacher(Teacher teacherDataBase, ClassRoom classRoom) {
        teacherDataBase.setClassRoom(classRoom);
        teacherRepository.save(teacherDataBase);
        classRoom.setTeacher(teacherDataBase);
        classRepository.save(classRoom);
    }

}
