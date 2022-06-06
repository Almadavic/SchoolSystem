package application.repository;

import application.entity.ClassRoom;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.NoSuchElementException;

@SpringBootTest
public class ClassRoomRepositoryTest {

    @Autowired
    private ClassRoomRepository classRoomRepository;

    @Test
    public void findByIdSuccess() {

        Long idClass = 1l;

        ClassRoom classRoom = classRoomRepository.findById(idClass).get();

        Assertions.assertEquals(classRoom.getId(),idClass);
    }


    @Test
    public void findByIdFail() {

        Long idClass = 97l;
        Assertions.assertThrows(NoSuchElementException.class, () -> classRoomRepository.findById(idClass).get());
    }

}
