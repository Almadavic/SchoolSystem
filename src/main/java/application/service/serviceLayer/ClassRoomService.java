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
import application.repository.UserRepository;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class ClassRoomService  {

    @Autowired
    private ClassRoomRepository classRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private UserRepository userRepository;

    @Cacheable(value = "classesRoomList")           // Aplicando Cache
    public Page<ClassRoomDto> findAll(Pageable pagination) {
        Page<ClassRoom> classes = classRepository.findAll(pagination);
        Page<ClassRoomDto> classesRoomDtos = classes.map(ClassRoomDto::new);
        return classesRoomDtos;
    }

    public ClassRoomDto findById(Long id,Principal user) {
        ClassRoom classRoom = returnClass(id);
        teacherAllowed(classRoom,user);
        ClassRoomDto classRoomDto = new ClassRoomDto(classRoom);
        return classRoomDto;
    }

    @Cacheable(value = "studentsListByClassRoom")
    public Page<StudentDto> findStudentsByClass(Long idClass, Pageable pagination,Principal user) {
        ClassRoom classRoom = returnClass(idClass);
        teacherAllowed(classRoom, user);
        Page<Student> students = studentRepository.findListStudentsByClassRoomId(idClass, pagination); // Cache não está funcionando aqui
        Page<StudentDto> studentDtos = students.map(StudentDto::new);
        return studentDtos;
    }


    public StudentDto findStudentById(Long idClass, Long idStudent, Principal user) {
        ClassRoom classRoom = returnClass(idClass);
        teacherAllowed(classRoom, user);
        Student student = returnStudent(classRoom,idStudent,false);
        return new StudentDto(student);
    }

   public TeacherDto findTeacher(Long idClass, Principal user) {
        ClassRoom classRoom =  returnClass(idClass);
        teacherAllowed(classRoom,user);
        Optional<Teacher> teacher = teacherRepository.findByClassRoomId(idClass);
        return new TeacherDto(teacher.orElseThrow(()->  new ThereIsntTeacherInThisClassException("This class doesn't have any teacher")));
    }

    @CacheEvict(value = {"classesRoomList", "studentsListByClassRoom","studentsList"}, allEntries = true)
    public StudentDto updateGrades(Long idClass, Long idStudent, Principal user, NewGradesForm newGrades) {
        List<UpdateCheck> validations = Arrays.asList(new GradeLimit(), new TeacherAllowed());

        ClassRoom classRoom = returnClass(idClass);
        Student student = returnStudent(classRoom, idStudent,false);

        validations.forEach(v -> v.validation(newGrades, classRoom, user));

        updateGrades(student, newGrades);
        approve(student);
        student = userRepository.save(student);
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
            validations.forEach(v -> v.validation(teacher, classTeacher));           // MÉTODO NÃO FUNCIONAL!
        } else {
            validations.forEach(v -> v.validation(teacher, null));
        }
        if (classRoom.getTeacher() != null) {
           classRoom.getTeacher().setClassRoom(null);
        }

       updateClassTeacher(teacher,classRoom); // ATUALIZA SALVANDO NO BANCO AS NOVAS INFORMAÇÕES !

        return new ClassRoomDto(classRoom);
    }


    @CacheEvict(value = {"classesRoomList", "studentsList","studentsListByClassRoom"}, allEntries = true)
    public ClassRoomDto addStudent(Long idClass, AddRemoveStudentForm addStudentForm) {
        List<AddStudentCheck> validations = Arrays.asList(new FullList(), new ClassContainsSameStudent(), new StudentHasAnotherClass());

        ClassRoom classRoom = returnClass(idClass);
        Long idStudent = addStudentForm.getIdStudent();
        Student student = returnStudent(classRoom,idStudent, true);


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
        Student student = returnStudent(classRoom,idStudent, false);
        student.setClassRoom(null);
        classRoom.removeStudent(student);
        classRoom = classRepository.save(classRoom);

        return new ClassRoomDto(classRoom);
    }

    @CacheEvict(value = {"teachersList","classesRoomList"})
    public ClassRoomDto removeTeacher(Long idClass) {

        ClassRoom classRoom = returnClass(idClass);
        classRoom.getTeacher().setClassRoom(null);
        classRoom.setTeacher(null);
        classRoom = classRepository.save(classRoom);

        return new ClassRoomDto(classRoom);
    }

    @CacheEvict(value = {"classesRoomList"})
    public String removeClass(Long idClass) {      // Só é possivel remover uma classe caso ela não tenha estudante nem professores!

        ClassRoom classRoom = returnClass(idClass);
       try {
           classRepository.delete(classRoom);
       }catch(DataIntegrityViolationException e) {
           throw new DatabaseException("You can't delete a class that has teachers and students, remove them first!");
       }
        return "Class : "+idClass+" removed!";
    }

    private void teacherAllowed(ClassRoom classRoom , Principal user) {
        TeacherAllowed validation = new TeacherAllowed();
          validation.validation(null,classRoom,user);
    }


    private ClassRoom returnClass(Long idClass) {
        Optional<ClassRoom> classRoom = classRepository.findById(idClass);
        return classRoom.orElseThrow(()-> new ResourceNotFoundException("Id : " + idClass + ", This class wasn't found on DataBase"));
    }

    private Student returnStudent(ClassRoom classRoom,Long idStudent,  boolean addMethod) {   // boolean addMethod - Se for o metodo de adicionar chamando,
        // pois se for, tem q chamar uma exception especifica e tirar outra.
        Optional<Student> studentOptionalDataBase = studentRepository.findById(idStudent); // Pesquisar se o aluno existe no BANCO, se n existir, erro na linha 220!
        if (!addMethod) {  // SE NÃO FOR O MÉTODO DE ADICIONAR
            Student studentOptionalClass = studentRepository.findStudentById(classRoom.getId(),idStudent)// Pesquisa se o aluno existe nessa CLASSE!
                    .orElseThrow(()-> new StudentDoesntExistInThisClassException("This student isn't in this classroom")); // Se não existir, ERRO!
        }
        return studentOptionalDataBase.orElseThrow(()->new ResourceNotFoundException("Id : " + idStudent + ", This student wasn't found on DataBase"));
    }

    private Teacher returnTeacher(Long idTeacher) {
        Optional<Teacher> teacher = teacherRepository.findById(idTeacher);
        return teacher.orElseThrow(()-> new ResourceNotFoundException("Id : " + idTeacher + ", This teacher wasn't found on DataBase"));
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

    private ClassRoom updateClassTeacher(Teacher teacher, ClassRoom classRoom) {

        teacher.setClassRoom(classRoom);
        teacherRepository.save(teacher);
         classRoom.setTeacher(teacher);
        classRoom = classRepository.save(classRoom);


        return classRoom;
    }


    private Character[] letteringVector() {
        Character[] letters = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S'};
        return letters;
    }

}
