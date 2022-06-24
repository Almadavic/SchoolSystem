package application.integrationTest.repository;


import application.domain.entity.ClassRoom;
import application.domain.enumerated.ClassShift;
import application.domain.entity.user.Teacher;
import application.integrationTest.repository.interfaceRepository.GeneralExtendsRepositoryTest;
import application.repository.ClassRoomRepository;
import application.repository.TeacherRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;


@SpringBootTest
@Profile(value = "test")
public class ClassRoomRepositoryTest implements GeneralExtendsRepositoryTest { // TESTE DE INTEGRAÇÃO

    @Autowired
    private ClassRoomRepository classRepository;

    @Autowired
    private TeacherRepository teacherRepository; // Necessário para testar o update!

    @Test
    @Override
    public void save() { // Salva uma Classe no Sistema.

        ClassRoom classRoom = new ClassRoom('B', ClassShift.AFTERNOON);
        classRoom = classRepository.save(classRoom);
        ClassRoom classRoomDataBase = classRepository.findById(classRoom.getId()).get();
        Assertions.assertEquals(classRoom.getId(), classRoomDataBase.getId());
        classRepository.delete(classRoomDataBase);
    }

     @Test
     void updateClassRoom() { // Atualiza uma classe no sistema.

        Long idClass = 1L;
        ClassRoom classRoom = classRepository.findById(idClass).get();
        Assertions.assertEquals("raphael@gmail.com", classRoom.getTeacher().getEmail());
        Teacher teacherClass = classRoom.getTeacher();
        removeTeacher(teacherClass, classRoom);
        Long idTeacher = 7L;
        Teacher teacherDataBase = teacherRepository.findById(idTeacher).get();
        setTeacher(teacherDataBase, classRoom);

        ClassRoom classRoomDataBase = classRepository.findById(idClass).get();
        Assertions.assertEquals("euni@gmail.com", classRoomDataBase.getTeacher().getEmail());

        removeTeacher(classRoom.getTeacher(), classRoom);

        setTeacher(teacherClass, classRoom);

        ClassRoom classRoomDataBase2 = classRepository.findById(idClass).get();
        Assertions.assertEquals("raphael@gmail.com", classRoomDataBase2.getTeacher().getEmail());
    }

    private void removeTeacher(Teacher teacherClass, ClassRoom classRoom) { // Remove o Professor de uma sala.
        teacherClass.setClassRoom(null);
        classRoom.setTeacher(null);
        teacherRepository.save(teacherClass);
    }

    private void setTeacher(Teacher teacherDataBase, ClassRoom classRoom) { // Seta o Professor em uma sala.
        teacherDataBase.setClassRoom(classRoom);
        teacherRepository.save(teacherDataBase);
        classRoom.setTeacher(teacherDataBase);
        classRepository.save(classRoom);
    }

}
