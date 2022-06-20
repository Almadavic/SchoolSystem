package application.services.serviceLayer;

import application.dtos.ClassRoomDto;
import application.entities.enums.ClassShift;
import application.entities.enums.Situation;
import application.entities.users.User;
import application.forms.*;
import application.dtos.StudentDto;
import application.dtos.TeacherDto;
import application.entities.ClassRoom;
import application.entities.users.Student;
import application.entities.users.Teacher;
import application.repositories.ClassRoomRepository;
import application.repositories.StudentRepository;
import application.repositories.TeacherRepository;
import application.repositories.UserRepository;
import application.services.businessRules.addStudent.AddStudentCheck;
import application.services.businessRules.createClass.CreateClassCheck;
import application.services.businessRules.setTeacher.SetTeacherCheck;
import application.services.businessRules.updateGrades.TeacherAllowed;
import application.services.businessRules.updateGrades.UpdateGradesCheck;
import application.services.exceptions.classRoomService.StudentDoesntExistInThisClassException;
import application.services.exceptions.classRoomService.ThereIsntTeacherInThisClassException;
import application.services.exceptions.general.DatabaseException;
import application.services.exceptions.general.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
public class ClassRoomService {

    @Autowired
    private ClassRoomRepository classRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private List<UpdateGradesCheck> validationsUpdateGrades; // Validações para atualizar uma nota.
    @Autowired
    private List<CreateClassCheck> validationsCreateClass; // Validações para criar uma sala no sistema.
    @Autowired
    private List<SetTeacherCheck> validationsSetTeacher; // Validações para setar um professor em uma classe.
    @Autowired
    private List<AddStudentCheck> validationsAddStudent; // Validação para adicionar um aluno em uma classe.

    @Cacheable(value = "classesRoomList")           // Aplicando Cache
    public Page<ClassRoomDto> findAll(Pageable pagination) {    // Método que me retorna uma paginação de todas as classes da plataforma, tendo algumas conf padrões para essa paginação.
        Page<ClassRoom> classRooms = classRepository.findAll(pagination);
        return classRooms.map(ClassRoomDto::new);
    }

    public ClassRoomDto findById(Long id, Principal user) { // Método me retorna uma classe da plataforma dado o Id da classe.
        ClassRoom classRoom = returnClass(id);
        teacherAllowed(classRoom, user);
        return new ClassRoomDto(classRoom);
    }

    @Cacheable(value = "studentsListByClassRoom")
    public Page<StudentDto> findStudentsByClass(Long idClass, Pageable pagination, Principal user) { // Método me retorna uma lista de alunos associados á uma classe dado o id dessa classe.
        ClassRoom classRoom = returnClass(idClass);
        teacherAllowed(classRoom, user);
        Page<Student> students = studentRepository.findListStudentsByClassRoomId(idClass, pagination);
        return students.map(StudentDto::new);
    }


    public StudentDto findStudentById(Long idClass, Long idStudent, Principal user) { // Método me retorna um aluno de uma certa classe, passando o id da classe e do aluno específico.
        ClassRoom classRoom = returnClass(idClass);
        teacherAllowed(classRoom, user);
        Student student = returnStudent(classRoom, idStudent, false);
        return new StudentDto(student);
    }

    public TeacherDto findTeacher(Long idClass, Principal user) { // Método me retorna o professor associado á classe dado o id da classe.
        ClassRoom classRoom = returnClass(idClass);
        teacherAllowed(classRoom, user);
        Optional<Teacher> teacher = teacherRepository.findTeacherByClassRoomId(idClass);
        return new TeacherDto(teacher.orElseThrow(() -> new ThereIsntTeacherInThisClassException("This class doesn't have any teacher")));
    }

    @CacheEvict(value = {"classesRoomList", "studentsListByClassRoom", "studentsList"}, allEntries = true)
    public StudentDto updateGrades(Long idClass, Long idStudent, Principal user, NewGradesForm newGrades) { // Método atualiza as notas de um aluno de uma respectiva sala, passando o id do aluno e da classe.

        ClassRoom classRoom = returnClass(idClass);
        Student student = returnStudent(classRoom, idStudent, false);

        validationsUpdateGrades.forEach(v -> v.validation(newGrades, classRoom, user)); // VALIDANDO!

        updateGrades(student, newGrades);
        approve(student);
        student = userRepository.save(student);
        return new StudentDto(student);
    }

    @CacheEvict(value = "classesRoomList", allEntries = true)
    public ClassRoomDto createClassRoom(CreateClassForm createClassForm) { // Método cria uma nova sala na plataforma, com a info Letter automatizada.

        List<ClassRoom> classRooms = classRepository.findAll();

        validationsCreateClass.forEach(v -> v.validation(classRooms)); // VALIDANDO!

        char letterNewClass = '.';
        if (classRooms.size() > 0) {
           char lastClassLetter = classRooms.get(classRooms.size() - 1).getLetter();
            Character[] vector = letteringVector();
            for (int i = 0; i < vector.length; i++) {
                if (lastClassLetter == vector[i]) {
                    letterNewClass = vector[i + 1];
                }
            }
        } else {
            letterNewClass = 'A';
        }


        ClassShift classShift = ClassShift.valueOf(createClassForm.getClassShift().toUpperCase());
        ClassRoom classRoom = new ClassRoom(letterNewClass, classShift);


        classRoom = classRepository.save(classRoom);
        return new ClassRoomDto(classRoom);
    }


    @CacheEvict(value = {"classesRoomList", "teachersList"}, allEntries = true)
    public ClassRoomDto setTeacher(Long idClass, SetTeacherForm setTeacherForm) { // Método seta o professor em uma classe.

        ClassRoom classRoom = returnClass(idClass);
        Long idTeacher = setTeacherForm.getIdTeacher();
        Teacher teacher = returnTeacher(idTeacher);
        Teacher classTeacher = classRoom.getTeacher();
        if (classTeacher != null) {
            validationsSetTeacher.forEach(v -> v.validation(teacher, classTeacher)); // VALIDANDO!
            classTeacher.setClassRoom(null);
        } else {
            validationsSetTeacher.forEach(v -> v.validation(teacher, null)); // VALIDANDO!
        }
        updateClassTeacher(teacher, classRoom); // ATUALIZA SALVANDO NO BANCO AS NOVAS INFORMAÇÕES !

        return new ClassRoomDto(classRoom);
    }


    @CacheEvict(value = {"classesRoomList", "studentsList", "studentsListByClassRoom"}, allEntries = true)
    public ClassRoomDto addStudent(Long idClass, AddRemoveStudentForm addStudentForm) {  // Método adiciona um aluno na classe.

        ClassRoom classRoom = returnClass(idClass);
        Long idStudent = addStudentForm.getIdStudent();
        Student student = returnStudent(classRoom, idStudent, true);


        ClassRoom finalClassRoom = classRoom;
        validationsAddStudent.forEach(v -> v.validation(student, finalClassRoom)); // VALIDANDO!

        student.setClassRoom(classRoom);

        classRoom.addStudent(student);

        classRoom = classRepository.save(classRoom);

        return new ClassRoomDto(classRoom);
    }

    @CacheEvict(value = {"classesRoomList", "studentsList", "studentsListByClassRoom"}, allEntries = true)
    public ClassRoomDto removeStudent(Long idClass, AddRemoveStudentForm removeStudentForm) { // Método remove um aluno da classe.

        ClassRoom classRoom = returnClass(idClass);
        Long idStudent = removeStudentForm.getIdStudent();
        Student student = returnStudent(classRoom, idStudent, false);
        student.setClassRoom(null);
        classRoom.removeStudent(student);
        classRoom = classRepository.save(classRoom);

        return new ClassRoomDto(classRoom);
    }

    @CacheEvict(value = {"teachersList", "classesRoomList"})
    public ClassRoomDto removeTeacher(Long idClass) { // Método remove o professor da sala.

        ClassRoom classRoom = returnClass(idClass);
        classRoom.getTeacher().setClassRoom(null);
        classRoom.setTeacher(null);
        classRoom = classRepository.save(classRoom);

        return new ClassRoomDto(classRoom);
    }

    @CacheEvict(value = {"classesRoomList"})
    public String removeClass(Long idClass) {     //  Método apaga uma classe do sistema.

        ClassRoom classRoom = returnClass(idClass);
        try {
            classRepository.delete(classRoom);  // Só é possivel remover uma classe caso ela não tenha estudantes nem professor, se tiver, vai dar erro, e vai lançar a
        } catch (DataIntegrityViolationException e) {                             // exception da linha 205.
            throw new DatabaseException("You can't delete a class that has teachers and students, remove them first!");
        }
        return "Class : " + idClass + " removed!";
    }

    private void teacherAllowed(ClassRoom classRoom, Principal user) { //  Verifica se o teacher está permitido a fazer algo.
        User userEntity = userRepository.findByEmail(user.getName()).get();
        if (userEntity instanceof Teacher) {
            TeacherAllowed validation = new TeacherAllowed();
            validation.validation(null, classRoom, user);
        }
    }

    private ClassRoom returnClass(Long idClass) { // Método que retorna uma Classe do banco.
        Optional<ClassRoom> classRoom = classRepository.findById(idClass);
        return classRoom.orElseThrow(() -> new ResourceNotFoundException("Id : " + idClass + ", This class wasn't found on DataBase"));
    }

    private Student returnStudent(ClassRoom classRoom, Long idStudent, boolean addMethod) {   // boolean addMethod - Se for o metodo de adicionar chamando,
        // pois se for, tem q chamar uma exception especifica e tirar outra.
        Optional<Student> studentOptionalDataBase = studentRepository.findById(idStudent); // Pesquisar se o aluno existe no BANCO, se n existir, erro na linha 220!
        if (!addMethod) {  // SE NÃO FOR O MÉTODO DE ADICIONAR
            studentRepository.findStudentByClassId(classRoom.getId(), idStudent)// Pesquisa se o aluno existe nessa CLASSE!
                    .orElseThrow(() -> new StudentDoesntExistInThisClassException("This student isn't in this classroom")); // Se não existir, ERRO!
        }
        return studentOptionalDataBase.orElseThrow(() -> new ResourceNotFoundException("Id : " + idStudent + ", This student wasn't found on DataBase"));
    }

    private Teacher returnTeacher(Long idTeacher) { // Método que retorna um Professor do banco.
        Optional<Teacher> teacher = teacherRepository.findById(idTeacher);
        return teacher.orElseThrow(() -> new ResourceNotFoundException("Id : " + idTeacher + ", This teacher wasn't found on DataBase"));
    }

    private void approve(Student student) {               // Aprova um estudante, troca o status para = APROVADO !
        if (student.getReportCard().getAverageStudent() >= 6) {
            student.getReportCard().setSituation(Situation.APPROVED);
        }
    }

    private void updateGrades(Student student, NewGradesForm newGrades) { // Verifica se o form (requisição) não tem valores nulos, se não tiver, vai atualizar a nota.

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

    private void updateClassTeacher(Teacher teacher, ClassRoom classRoom) { // Atualiza o professor de uma classe.

        teacher.setClassRoom(classRoom);
        userRepository.save(teacher);
        classRoom.setTeacher(teacher);
        classRepository.save(classRoom);
    }


    private Character[] letteringVector() { // Método com as possíveis letras de Classes, Quando uma sala é criada, ela recebe a próxima letra!
        return new Character[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S'};
    }

}
