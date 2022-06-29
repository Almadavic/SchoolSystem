package application.integrationTest.repository;


import application.entity.user.Student;
import application.integrationTest.repository.builder.StudentBuilder;
import application.integrationTest.repository.interfaceRepository.ExtendsUserRepositoryTest;
import application.repository.StudentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.NoSuchElementException;


@SpringBootTest
@Profile(value = "test")
public class StudentRepositoryTest implements ExtendsUserRepositoryTest { // TESTE DE INTEGRAÇÃO

    @Autowired
    private StudentRepository studentRepository;

    @Test
    void findStudentsByClassId() { // Encontra os estudantes de uma classe.
        //----- Teste da Sala 1 ---- \\
        Long idClass1 = 1L;
        Pageable pagination = PageRequest.of(0, 10);
        Page<Student> students1 = studentRepository.findListStudentsByClassRoomId(idClass1, pagination);
        Assertions.assertEquals(3, students1.getContent().size());


        //----- Teste da Sala 2 ---- \\
        Long idClass2 = 2L;
        Page<Student> students2 = studentRepository.findListStudentsByClassRoomId(idClass2, pagination);
        Assertions.assertEquals(0, students2.getContent().size());

        //------ Teste Ambos -------- \\

        Assertions.assertEquals(10, students1.getSize());
        Assertions.assertEquals(0, students1.getPageable().getPageNumber());
    }


    @Test
    void studentExistsInClass() { // Testa se um estudante existe em determinado sala.

        Long idClass = 1L;
        Long idStudent = 2L;
        Student student = studentRepository.findStudentByClassId(idClass, idStudent).get();
        Assertions.assertDoesNotThrow(() -> NoSuchElementException.class);
        Assertions.assertEquals("almadavic@live.com", student.getEmail());
    }

    @Test
    void studentDoesntExistsInClass() { // Testa se um estudante NÃO existe em determinado sala.

        Long idClass = 1L;
        Long idStudent = 4L;
        Assertions.assertThrows(NoSuchElementException.class, () -> studentRepository.findStudentByClassId(idClass, idStudent).get());
    }

    @Test
    @Override
    public void listUsersWhoDontHaveClass() { // Testa se lista de estudantes, todos os estudantes que não estão em nenhuma sala.

        List<Student> students = studentRepository.findAllWhereClassRoomIsNull();
        boolean hasClass = false;
        if (students.stream().anyMatch(student -> student.getClassRoom() != null)) {
            hasClass = true;
        }
        Assertions.assertFalse(hasClass);
        Assertions.assertEquals(1, students.size());
    }

    @Test
    @Override
    public void save() { // Salva um aluno no sistema.

        Student student = (Student) new StudentBuilder().setNome("Paulo").setEmail("paulo@gmail.com").setPassword("123456").setClassRoom(null).create();
        student = studentRepository.save(student);
        Student studentDataBase = studentRepository.findById(student.getId()).get();
        Assertions.assertEquals(student.getId(), studentDataBase.getId());
        studentRepository.delete(student);
    }

    @Test
    void updateGrades() { // Atualiza as notas de um aluno.

        Long idStudent = 2L;
        Student student = studentRepository.findById(idStudent).get();

        Assertions.assertEquals(0.0, student.getReportCard().getGrade1());
        Assertions.assertEquals(0.0, student.getReportCard().getGrade2());
        Assertions.assertEquals(0.0, student.getReportCard().getGrade3());
        Assertions.assertEquals(0.0, student.getReportCard().getAverageStudent());

        student.getReportCard().setGrade1(5.0);
        student.getReportCard().setGrade2(7.0);
        student.getReportCard().setGrade3(3.0);

        studentRepository.save(student);

        Student studentDataBase = studentRepository.findById(idStudent).get();


        Assertions.assertEquals(5.0, studentDataBase.getReportCard().getGrade1());
        Assertions.assertEquals(7.0, studentDataBase.getReportCard().getGrade2());
        Assertions.assertEquals(3.0, studentDataBase.getReportCard().getGrade3());
        Assertions.assertEquals(5.0, studentDataBase.getReportCard().getAverageStudent());


    }
}
