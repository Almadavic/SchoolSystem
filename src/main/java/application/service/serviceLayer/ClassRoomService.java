package application.service.serviceLayer;


import application.dto.ClassRoomDto;
import application.entity.ClassShift;
import application.form.*;
import application.dto.StudentDto;
import application.dto.TeacherDto;
import application.entity.ClassRoom;
import application.entity.users.Student;
import application.entity.users.Teacher;
import application.repository.ClassRoomRepository;
import application.repository.StudentRepository;
import application.repository.TeacherRepository;
import application.service.businessRule.addStudent.AddStudentCheck;
import application.service.businessRule.addStudent.ClassContainsSameStudent;
import application.service.businessRule.addStudent.StudentHasAnotherClass;
import application.service.businessRule.setTeacher.SameTeacher;
import application.service.businessRule.setTeacher.SetTeacherCheck;
import application.service.businessRule.setTeacher.TeacherHasAnotherClass;
import application.service.businessRule.updateGrades.GradeLimit;
import application.service.businessRule.updateGrades.TeacherAllowed;
import application.service.businessRule.updateGrades.UpdateCheck;
import application.service.exception.classRoomService.ChangeSameTeacher;
import application.service.exception.classRoomService.StudentDoesntExistInThisClass;
import application.service.exception.classRoomService.TeacherBelongsAnotherClass;
import application.service.exception.classRoomService.ThereIsntTeacherInThisClass;
import application.service.exception.general.ResourceNotFoundException;
import application.service.serviceLayer.interfacee.GenericMethodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class ClassRoomService implements GenericMethodService {

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

    @Override
    public ClassRoomDto findById(Long id) {
        ClassRoom classRoom = returnClass(id);
        ClassRoomDto classRoomDto = new ClassRoomDto(classRoom);
        return classRoomDto;
    }

    public Page<StudentDto> findStudentsByClass(Long idClass, Pageable pagination) {
        returnClass(idClass); // Coloquei aqui para ver se roda a exception , para ver se a classe existe!
        Page<Student> students = studentRepository.findListStudentsByClassRoomId(idClass, pagination); // Cache não está funcionando aqui
        Page<StudentDto> studentDtos = students.map(StudentDto::new);
        return studentDtos;
    }


    public StudentDto findStudentById(Long idClass, Long idStudent) {             // refatorar nativeQuery
        ClassRoom classRoom = returnClass(idClass);
        Student student = returnStudent(idStudent, classRoom, false);
        return new StudentDto(student);
    }

    public TeacherDto findTeacher(Long idClass) {
        Optional<Teacher> teacher = teacherRepository.findByClassRoomId(idClass);
        if (teacher.isEmpty()) {
            throw new ThereIsntTeacherInThisClass("This class doesn't have any teacher");
        }
        return new TeacherDto(teacher.get());
    }

    @CacheEvict(value = {"classesRoomList","studentsListByClassRoom"}, allEntries = true)
    public StudentDto updateGrades(Long idClass, Long idStudent, Principal user, NewGradesForm newGrades) {
        List<UpdateCheck> validations = Arrays.asList(new GradeLimit(), new TeacherAllowed());

        ClassRoom classRoom = returnClass(idClass);
        Student student = returnStudent(idStudent, classRoom, false);

        validations.forEach(v -> v.validation(newGrades, classRoom, user));

        updateGrades(student, newGrades);
        student = studentRepository.save(student);
        return new StudentDto(student);
    }

    @CacheEvict(value = "classesRoomList", allEntries = true)
    public ClassRoomDto createClassRoom(CreateClassForm createClassForm) {
        List<ClassRoom> classRooms = classRepository.findAll();

        char lastClassLetter = classRooms.get(classRooms.size() - 1).getLetter();

        Character[] vector = letteringVector();
        char letterNewClass = '.';

        for (int i = 0; i < vector.length; i++) {
            if (lastClassLetter == vector[i]) {
                letterNewClass = vector[i + 1];
            }
        }
        ClassShift classShift = ClassShift.valueOf(createClassForm.getClassShift().toUpperCase());
        ClassRoom classRoom = new ClassRoom(letterNewClass, classShift);
        classRoom = classRepository.save(classRoom);
        return new ClassRoomDto(classRoom);
    }


    @CacheEvict(value = {"classesRoomList", "teachersList"}, allEntries = true)
    public ClassRoomDto setTeacher(Long idClass, SetTeacherForm setTeacherForm) {
        List<SetTeacherCheck> validations = Arrays.asList(new TeacherHasAnotherClass(), new SameTeacher());

        ClassRoom classRoom = returnClass(idClass);
        Teacher classTeacher = classRoom.getTeacher();
        if (classRoom.getTeacher() != null) {
            classRoom.getTeacher().setClassRoom(null);       // Melhorar esse método se possível ! Esses null faz com que eu não consiga
            classRoom.setTeacher(null);                     // verificar se o teacher que vou colocar em seguida é o msm q já tava, pq sempre vou setar nullo.
            classRoom = classRepository.save(classRoom);    // mas tem q fazer isso sem tirar esse bloco IF ao lado, NÃO MODIFICAR!!!
        }


        Long idTeacher = setTeacherForm.getIdTeacher();
        Teacher teacher = returnTeacher(idTeacher);

        Teacher finalTeacher = teacher;
        try {
            validations.forEach(v -> v.validation(finalTeacher, classTeacher));
            teacher.setClassRoom(classRoom);
            teacherRepository.save(teacher);
            classRoom.setTeacher(teacher);                     // ARRUMAR MÉTODO ! não consigo testar a exception : professor já está em outra classe.
            classRepository.save(classRoom);
        } catch (ChangeSameTeacher e) {
          setAndSaveAttributes(classTeacher,classRoom);
            throw new ChangeSameTeacher("The new teacher cannot be the same than the last teacher!"); //
        }catch (TeacherBelongsAnotherClass e) {
            setAndSaveAttributes(classTeacher,classRoom);
            throw new TeacherBelongsAnotherClass("The teacher already belongs an another class.");
        }


        return new ClassRoomDto(classRoom);
    }


    @CacheEvict(value = {"classesRoomList", "studentsList"}, allEntries = true)
    public ClassRoomDto addStudent(Long idClass, AddRemoveStudentForm addStudentForm) {
        List<AddStudentCheck> validations = Arrays.asList(new ClassContainsSameStudent(), new StudentHasAnotherClass());

        ClassRoom classRoom = returnClass(idClass);
        Long idStudent = addStudentForm.getIdStudent();
        Student student = returnStudent(idStudent, classRoom, true);

        ClassRoom finalClassRoom = classRoom;
        validations.forEach(v -> v.validation(student, finalClassRoom));

        student.setClassRoom(classRoom);

        classRoom.addStudent(student);

        classRoom = classRepository.save(classRoom);

        return new ClassRoomDto(classRoom);
    }

    @CacheEvict(value = {"classesRoomList", "studentsList"}, allEntries = true)
    public ClassRoomDto removeStudent(Long idClass, AddRemoveStudentForm removeStudentForm) {

        ClassRoom classRoom = returnClass(idClass);
        Long idStudent = removeStudentForm.getIdStudent();
        Student student = returnStudent(idStudent, classRoom, false);
        student.setClassRoom(null);
        classRoom.getStudents().removeIf(s -> s.getId().equals(student.getId()));
        classRoom = classRepository.save(classRoom);

        return new ClassRoomDto(classRoom);


    }


    private ClassRoom returnClass(Long idClass) {
        Optional<ClassRoom> classRoom = classRepository.findById(idClass);
        if (classRoom.isEmpty()) {
            throw new ResourceNotFoundException("Id : " + idClass + ", This class wasn't found on DataBase");
        }
        return classRoom.get();
    }

    private Student returnStudent(Long idStudent, ClassRoom classRoom, boolean addMethod) {   // boolean addMethod - Se for o metodo de adicionar chamando,
        // pois se for, tem q chamar uma exception especifica e tirar outra.
        Optional<Student> student = studentRepository.findById(idStudent);
        studentDoesNotExist(student, idStudent);
        if (!addMethod) {
            studentDoesntExistInThisClass(student, classRoom);
        }
        return student.get();
    }

    private void studentDoesNotExist(Optional<Student> student, Long idStudent) {
        if (student.isEmpty()) {
            throw new ResourceNotFoundException("Id : " + idStudent + ", This student wasn't found on DataBase");
        }

    }

    private void studentDoesntExistInThisClass(Optional<Student> student, ClassRoom classRoom) {
        boolean exists = false;
        for (Student studentList : classRoom.getStudents()) {
            if (studentList.getId() == student.get().getId()) {                  // A classe está funcional, mas pode melhorar mais a arquitetura!
                exists = true;
            }
        }
        if (!exists) {
            throw new StudentDoesntExistInThisClass("Doesn't have this student in this class");
        }
    }

    private Teacher returnTeacher(Long idTeacher) {
        Optional<Teacher> teacher = teacherRepository.findById(idTeacher);
        if (teacher.isEmpty()) {
            throw new ResourceNotFoundException("Id : " + idTeacher + ", This teacher wasn't found on DataBase");
        }
        return teacher.get();
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

    private void setAndSaveAttributes(Teacher classTeacher, ClassRoom classRoom) {
        classTeacher.setClassRoom(classRoom);
        teacherRepository.save(classTeacher);
        classRoom.setTeacher(classTeacher);
        classRepository.save(classRoom);
    }


    private Character[] letteringVector() {
        Character[] letters = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S'};
        return letters;
    }


}
