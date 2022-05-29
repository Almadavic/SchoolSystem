package application.service.serviceLayer;


import application.dto.ClassRoomDto;
import application.entity.ClassShift;
import application.entity.Situation;
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
import application.service.businessRule.addStudent.FullList;
import application.service.businessRule.addStudent.StudentHasAnotherClass;
import application.service.businessRule.setTeacher.SameTeacher;
import application.service.businessRule.setTeacher.SetTeacherCheck;
import application.service.businessRule.setTeacher.TeacherHasAnotherClass;
import application.service.businessRule.updateGrades.GradeLimit;
import application.service.businessRule.updateGrades.TeacherAllowed;
import application.service.businessRule.updateGrades.UpdateCheck;
import application.service.exception.classRoomService.StudentDoesntExistInThisClassException;
import application.service.exception.classRoomService.ThereIsntTeacherInThisClassException;
import application.service.exception.general.DatabaseException;
import application.service.exception.general.ResourceNotFoundException;
import application.service.serviceLayer.interfacee.GenericMethodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.dao.DataIntegrityViolationException;
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
            throw new ThereIsntTeacherInThisClassException("This class doesn't have any teacher");
        }
        return new TeacherDto(teacher.get());
    }

    @CacheEvict(value = {"classesRoomList", "studentsListByClassRoom"}, allEntries = true)
    public StudentDto updateGrades(Long idClass, Long idStudent, Principal user, NewGradesForm newGrades) {
        List<UpdateCheck> validations = Arrays.asList(new GradeLimit(), new TeacherAllowed());

        ClassRoom classRoom = returnClass(idClass);
        Student student = returnStudent(idStudent, classRoom, false);

        validations.forEach(v -> v.validation(newGrades, classRoom, user));

        updateGrades(student, newGrades);
        approve(student);
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
        List<SetTeacherCheck> validations = Arrays.asList(new SameTeacher(),new TeacherHasAnotherClass());

        ClassRoom classRoom = returnClass(idClass);
        Long idTeacher = setTeacherForm.getIdTeacher();
        Teacher teacher = returnTeacher(idTeacher);
        Teacher classTeacher = classRoom.getTeacher();
        if (classRoom.getTeacher() != null) {
            validations.forEach(v -> v.validation(teacher, classTeacher));
        } else {
            validations.forEach(v -> v.validation(teacher, null));
        }
        if (classRoom.getTeacher() != null) {
           classRoom.getTeacher().setClassRoom(null);
        }

        teacher.setClassRoom(classRoom);  // DESC DO MÉTODO : EU SÓ POSSO COLOCAR UM PROFESSOR QUE NÃO TENHA NENHUMA SALA
        teacherRepository.save(teacher); // E NÃO SEJA O MESMO PROFESSOR DESSA!
        classRoom.setTeacher(teacher);                     // MÉTODO PARECE ESTAR 100% Funcional, NÃO ALTERAR A LÓGICA!!!!! APENAS ARRUMAR A ARQUITETURA DO CÓDIGO.
        classRepository.save(classRoom);

        return new ClassRoomDto(classRoom);
    }


    @CacheEvict(value = {"classesRoomList", "studentsList","studentsListByClassRoom"}, allEntries = true)
    public ClassRoomDto addStudent(Long idClass, AddRemoveStudentForm addStudentForm) {
        List<AddStudentCheck> validations = Arrays.asList(new FullList(), new ClassContainsSameStudent(), new StudentHasAnotherClass());

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

    @CacheEvict(value = {"classesRoomList", "studentsList","studentsListByClassRoom"}, allEntries = true)
    public ClassRoomDto removeStudent(Long idClass, AddRemoveStudentForm removeStudentForm) {

        ClassRoom classRoom = returnClass(idClass);
        Long idStudent = removeStudentForm.getIdStudent();
        Student student = returnStudent(idStudent, classRoom, false);
        student.setClassRoom(null);
        classRoom.getStudents().removeIf(s -> s.getId().equals(student.getId()));
        classRoom = classRepository.save(classRoom);

        return new ClassRoomDto(classRoom);
    }

    public ClassRoomDto removeTeacher(Long idClass) {

        ClassRoom classRoom = returnClass(idClass);
        classRoom.getTeacher().setClassRoom(null);
        classRoom.setTeacher(null);
        classRoom = classRepository.save(classRoom);

        return new ClassRoomDto(classRoom);
    }

    public String removeClass(Long idClass) {      // Só é possivel remover uma classe caso ela não tenha estudante nem professores!

        ClassRoom classRoom = returnClass(idClass);
       try {
           classRepository.delete(classRoom);
       }catch(DataIntegrityViolationException e) {
           throw new DatabaseException("You can't delete a class that has teachers and students!");
       }
        return "Class : "+idClass+" removed!";
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
            throw new StudentDoesntExistInThisClassException("Doesn't have this student in this class");
        }
    }

    private Teacher returnTeacher(Long idTeacher) {
        Optional<Teacher> teacher = teacherRepository.findById(idTeacher);
        if (teacher.isEmpty()) {
            throw new ResourceNotFoundException("Id : " + idTeacher + ", This teacher wasn't found on DataBase");
        }
        return teacher.get();
    }

    private void approve(Student student) {
        if(student.getReportCard().getAverageStudent() >=6) {
            student.getReportCard().setSituation(Situation.APPROVED);
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
