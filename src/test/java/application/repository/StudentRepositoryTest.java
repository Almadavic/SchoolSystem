package application.repository;


import application.entity.users.Student;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.NoSuchElementException;


@SpringBootTest
public class StudentRepositoryTest {

    @Autowired
    private StudentRepository studentRepository;


    @Test
    public void findByIdSuccess() {

        Long idStudent = 4L;
        Student student = studentRepository.findById(idStudent).get();
        Assertions.assertEquals(student.getId(),idStudent);
    }

    @Test
    public void findByIdFail() {

        Long idStudent = 9L;
        Assertions.assertThrows(NoSuchElementException.class, () -> studentRepository.findById(idStudent).get());
    }

    @Test
    public void findStudentsPerClassId() {

        Long idClass = 1l;
        Pageable pagination = PageRequest.of(0,10);
        Page<Student> students = studentRepository.findListStudentsByClassRoomId(idClass, pagination);
        Assertions.assertEquals(10,students.getSize());
    }



    @Test
    public void returnStudentDataBaseByIdSuccess() {

        Long id = 1L;
        Student student = studentRepository.findById(id).get();
        Assertions.assertEquals(student.getId(),id);
    }

    @Test
    public void returnStudentDataBaseByIdFail() {

       Long id = 15L;
       Assertions.assertThrows(NoSuchElementException.class ,()-> studentRepository.findById(id).get());
    }


    @Test
    public void studentExistsInClass() {

        Long idClass = 1l;
        Long idStudent = 1l;

        Student student = studentRepository.findStudentByClassId(idClass,idStudent).get();

        Assertions.assertDoesNotThrow(()-> NoSuchElementException.class);

    }

    @Test
    public void studentDoesntExistsInClass() {

        Long idClass = 1l;
        Long idStudent = 4l;
        Assertions.assertThrows(NoSuchElementException.class,()-> studentRepository.findStudentByClassId(idClass,idStudent).get());

    }

    @Test
    public void listStudentsWhoDontHaveClass() {

        List<Student> students = studentRepository.findAllWhereClassRoomIsNull();

        boolean hasClass = false;

        for (Student student : students) {
            if(student.getClassRoom()!=null) {
                hasClass = true;
            }
        }

        Assertions.assertFalse(hasClass);

    }

    @Test
    public void saveStudent() {
        Student student = new Student("Paulo","paulo@gmail.com","123456",null);
        student =  studentRepository.save(student);
        Student studentDataBase = studentRepository.findById(student.getId()).get();
        Assertions.assertEquals(student.getId(),studentDataBase.getId());
    }

}
