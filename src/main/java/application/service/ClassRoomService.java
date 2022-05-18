package application.service;

import application.dto.ClassRoomDto;
import application.form.NewGradesForm;
import application.dto.StudentDto;
import application.dto.TeacherDto;
import application.entity.ClassRoom;
import application.entity.Student;
import application.entity.Teacher;
import application.repository.ClassRoomRepository;
import application.repository.StudentRepository;
import application.repository.TeacherRepository;
import application.service.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ClassRoomService {

    @Autowired
    private ClassRoomRepository clasRepository;

    @Autowired
    private StudentRepository studentRepository; // não remover

    @Autowired
    private TeacherRepository teacherRepository; // não remover

    public Page<ClassRoomDto> findAll(Pageable pagination) {
        Page<ClassRoom> classes = clasRepository.findAll(pagination);
        Page<ClassRoomDto> classesRoomDtos = classes.map(ClassRoomDto::new);
        return classesRoomDtos;
    }

    public ClassRoomDto findById(Long id) {
        ClassRoom classRoom = errorNotFoundClassOtherwiseReturnClass(id);
        ClassRoomDto classRoomDto = new ClassRoomDto(classRoom);
        return classRoomDto;
    }

    public List<StudentDto> findStudentsByClass(Long id) {        // refatorar para native query
        ClassRoom classRoom = errorNotFoundClassOtherwiseReturnClass(id);
        List<Student> students = classRoom.getStudents();
        List<StudentDto> studentDtos = convertListFromEntityToDto(students);
        return studentDtos;
    }

    public StudentDto findStudentById(Long idClass, Long idStudent) { // refatorar nativeQuery
        ClassRoom classRoom = errorNotFoundClassOtherwiseReturnClass(idClass);
        Student student = errorNotFoundStudentOtherwiseReturnStudent(idStudent, classRoom);
        return new StudentDto(student);
    }

    public TeacherDto findTeacher(Long id) { // refatorar native query
        ClassRoom classRoom = errorNotFoundClassOtherwiseReturnClass(id);
        Teacher teacher = classRoom.getTeacher();
        return new TeacherDto(teacher);
    }


    public StudentDto updateGrades(Long idClass, Long idStudent, Principal principal, NewGradesForm newGrades) {
        ClassRoom classRoom = errorNotFoundClassOtherwiseReturnClass(idClass);
        Student student = errorNotFoundStudentOtherwiseReturnStudent(idStudent,classRoom);
        String teacherName = classRoom.getTeacher().getEmail();
        if (!teacherName.equals(principal.getName())) {
            throw new RuntimeException("Não tem permissão");
        }
        updateGrades(student, newGrades);
        student = studentRepository.save(student);
        return new StudentDto(student);
    }


    private List<StudentDto> convertListFromEntityToDto(List<Student> students) {
        List<StudentDto> studentsDtos = new ArrayList<>();
        for (Student student : students) {                              // Transformar em Stream
            studentsDtos.add(new StudentDto(student));
        }
        return studentsDtos;
    }

    private ClassRoom errorNotFoundClassOtherwiseReturnClass(Long id) {
        Optional<ClassRoom> classRoom = clasRepository.findById(id);
        if (classRoom.isEmpty()) {
            throw new ResourceNotFoundException("Class not found");
        }
        return classRoom.get();
    }

    private void updateGrades(Student student, NewGradesForm newGrades) {
        if (newGrades.getGrade1() != null) {
            student.getReportCard().setGrade1(newGrades.getGrade1());
        }

        if (newGrades.getGrade2() != null) {
            student.getReportCard().setGrade2(newGrades.getGrade2());
        }

        if (newGrades.getGrade3() != null) {
            student.getReportCard().setGrade3(newGrades.getGrade3());
        }
    }

    private Student errorNotFoundStudentOtherwiseReturnStudent(Long id, ClassRoom classRoom) {
        Student student = classRoom.getStudents().get(Integer.parseInt(String.valueOf(id-1)));
        return student;
    }

}
