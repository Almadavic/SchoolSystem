package application.integrationTest.repository;

import application.entity.ClassRoom;
import application.entity.user.Teacher;
import application.integrationTest.repository.builder.TeacherBuilder;
import application.repository.ClassRoomRepository;
import application.repository.TeacherRepository;
import application.integrationTest.repository.interfaceRepository.ExtendsUserRepositoryTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

import java.util.List;
import java.util.NoSuchElementException;

@SpringBootTest
@Profile(value = "test")
public class TeacherRepositoryTest implements ExtendsUserRepositoryTest { // TESTE DE INTEGRAÇÃO

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private ClassRoomRepository classRepository; // Foi necessário para utilizar o update


    @Test
    void teacherExistsInClass() { // Testa se o professor existe na classe.

        Long idClass = 1L;
        Teacher teacher = teacherRepository.findTeacherByClassRoomId(idClass).get();
        Assertions.assertDoesNotThrow(() -> NoSuchElementException.class);
        Assertions.assertEquals(teacher, teacher.getClassRoom().getTeacher());
        Assertions.assertEquals("raphael@gmail.com", teacher.getClassRoom().getTeacher().getEmail());
    }

    @Test
    void teacherDoesntExistInClass() { // Testa se o professor NÃO existe na classe.

        Long idClass = 2L;
        Assertions.assertThrows(NoSuchElementException.class, () -> teacherRepository.findTeacherByClassRoomId(idClass).get());
    }

    @Test
    @Override
    public void save() { // Salva um professor no banco.

        Teacher teacher = (Teacher) new TeacherBuilder()
                .setNome("Guilherme")
                .setEmail("guilherme@gmail.com")
                .setPassword("123456")
                .setClassRoom(null)
                .create();
        teacher = teacherRepository.save(teacher);
        Teacher teacherDataBase = teacherRepository.findById(teacher.getId()).get();
        Assertions.assertEquals(teacher.getId(), teacherDataBase.getId());
        teacherRepository.delete(teacher);
    }

    @Test
    void updateTeacher() { // Troca o teacher de classe.

        Long idTeacher = 6L;
        Teacher teacher = teacherRepository.findById(idTeacher).get();
        Assertions.assertEquals(idTeacher, teacher.getId());
        Assertions.assertEquals(1, teacher.getClassRoom().getId());

        Long idClass = 2L;
        ClassRoom classRoom = classRepository.findById(idClass).get();

        teacher.setClassRoom(classRoom);
        teacherRepository.save(teacher);
        classRoom.setTeacher(teacher);
        classRoom = classRepository.save(classRoom);

        Assertions.assertEquals(idTeacher, teacher.getId());
        Assertions.assertEquals(2, teacher.getClassRoom().getId());
        Assertions.assertEquals(teacher, classRoom.getTeacher());
    }


    @Test
    @Override
    public void listUsersWhoDontHaveClass() { // Testa se lista de professores, todos os professores que não estão em nenhuma sala.

        List<Teacher> teachers = teacherRepository.findAllWhereClassRoomIsNull();
        boolean hasClass = false;
        if (teachers.stream().anyMatch(teacher -> teacher.getClassRoom() != null)) {
            hasClass = true;
        }
        Assertions.assertFalse(hasClass);
        Assertions.assertEquals(1, teachers.size());
    }

}
