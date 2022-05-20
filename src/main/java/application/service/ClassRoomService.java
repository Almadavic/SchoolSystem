package application.service;

import application.businessRule.updateGrades.GradeLimit;
import application.businessRule.updateGrades.TeacherAllowed;
import application.businessRule.updateGrades.UpdateCheck;
import application.dto.ClassRoomDto;
import application.entity.ClassShift;
import application.form.NewGradesForm;
import application.dto.StudentDto;
import application.dto.TeacherDto;
import application.entity.ClassRoom;
import application.entity.users.Student;
import application.entity.users.Teacher;
import application.repository.ClassRoomRepository;
import application.repository.StudentRepository;
import application.repository.TeacherRepository;
import application.service.exception.NoPermissionException;
import application.service.exception.ResourceNotFoundException;
import application.service.exception.StudentAlreadyExists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class ClassRoomService {

    @Autowired
    private ClassRoomRepository classRepository;

    @Autowired
    private StudentRepository studentRepository; // não remover

    @Autowired
    private TeacherRepository teacherRepository; // não remover

    public Page<ClassRoomDto> findAll(Pageable pagination) {
        Page<ClassRoom> classes = classRepository.findAll(pagination);
        Page<ClassRoomDto> classesRoomDtos = classes.map(ClassRoomDto::new);
        return classesRoomDtos;
    }

    public ClassRoomDto findById(Long id) {
        ClassRoom classRoom = returnClass(id);
        ClassRoomDto classRoomDto = new ClassRoomDto(classRoom);
        return classRoomDto;
    }

    public Page<StudentDto> findStudentsByClass(Long idClass, Pageable pagination) {
        returnClass(idClass);
        Page<Student> students = studentRepository.findListStudentsByClassRoomId(idClass, pagination); // Cache não está funcionando aqui
        Page<StudentDto> studentDtos = students.map(StudentDto::new);
        return studentDtos;
    }


    public StudentDto findStudentById(Long idClass, Long idStudent) {             // refatorar nativeQuery
        ClassRoom classRoom = returnClass(idClass);
        Student student = returnStudent(idStudent, classRoom);
        return new StudentDto(student);
    }

    public TeacherDto findTeacher(Long idClass) {
        Optional<Teacher> teacher = teacherRepository.findByClassRoomId(idClass);
        if (teacher.isEmpty()) {
            throw new ResourceNotFoundException("No id passed, there is no teacher on this class");
        }
        return new TeacherDto(teacher.get());
    }

    @CacheEvict(value = "classesRoomList", allEntries = true)
    public StudentDto updateGrades(Long idClass, Long idStudent, Principal principal, NewGradesForm newGrades) {
        List<UpdateCheck> validations = Arrays.asList(new GradeLimit(), new TeacherAllowed());

        ClassRoom classRoom = returnClass(idClass);
        Student student = returnStudent(idStudent, classRoom);
        String teacherName = classRoom.getTeacher().getEmail();
        String userLoggedName = principal.getName();

        validations.forEach(v -> v.validation(newGrades, teacherName, userLoggedName));

        updateGrades(student, newGrades);
        student = studentRepository.save(student);
        return new StudentDto(student);
    }

    @CacheEvict(value = "classesRoomList", allEntries = true)
    public ClassRoomDto createClassRoom(String classShift) {
        List<ClassRoom> classRooms = classRepository.findAll();

        char lastClassLetter = classRooms.get(classRooms.size() - 1).getLetter();

        Character[] vector = letteringVector();
        char letterNewClass = '.';

        for (int i = 0; i < vector.length; i++) {
            if (lastClassLetter == vector[i]) {
                letterNewClass = vector[i + 1];
            }
        }
        ClassRoom classRoom = new ClassRoom(letterNewClass, ClassShift.valueOf(classShift));
        classRoom = classRepository.save(classRoom);
        return new ClassRoomDto(classRoom);
    }

    @CacheEvict(value = "classesRoomList", allEntries = true)
    public ClassRoomDto setTeacher(Long idClass, Long idTeacher) {

        ClassRoom classRoom = returnClass(idClass);
        Teacher teacher = teacherRepository.findById(idTeacher).get();
        classRoom.setTeacher(teacher);
        classRoom = classRepository.save(classRoom);
        return new ClassRoomDto(classRoom);
    }

    @CacheEvict(value = "classesRoomList", allEntries = true)
    public ClassRoomDto addStudent(Long idClass, Long idStudent) {

        ClassRoom classRoom = returnClass(idClass);
        Student student = returnStudent(idStudent, classRoom);

        boolean classRoomContainsNewStudent = checkIfStudentExistsInTheClassRoom(student, classRoom);      // Método pode ser refatorado depois com o uso de native query

        if (classRoomContainsNewStudent == true) {
            throw new StudentAlreadyExists("This class alreay contains the student");
        }
        classRoom.addStudent(student);

        classRoom = classRepository.save(classRoom);

        return new ClassRoomDto(classRoom);
    }


    private ClassRoom returnClass(Long id) {
        Optional<ClassRoom> classRoom = classRepository.findById(id);
        if (classRoom.isEmpty()) {
            throw new ResourceNotFoundException(id);
        }
        return classRoom.get();
    }

    private Student returnStudent(Long id, ClassRoom classRoom) {   // Método brevemente será apagado quando fizer nativeQuery
        long index = id - 1;
        try {
            Student student = classRoom.getStudents().get(Integer.parseInt(String.valueOf(index)));
            return student;
        } catch (IndexOutOfBoundsException e) {
            throw new ResourceNotFoundException(id);
        }
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


    private Character[] letteringVector() {
        Character[] letters = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S'};
        return letters;
    }

    private boolean checkIfStudentExistsInTheClassRoom(Student student, ClassRoom classRoom) {

        for (Student studentClass : classRoom.getStudents()) {
            if (studentClass.equals(student)) {
                return true;
            }
        }
        return false;
    }

}
