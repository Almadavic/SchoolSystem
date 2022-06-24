package application.service.serviceAction;

import application.dto.response.StudentDto;

import application.domain.vo.RegistrationVO;
import application.domain.entity.Role;
import application.domain.entity.user.Student;
import application.dto.request.RegisterUserForm;
import application.repository.RoleRepository;
import application.repository.StudentRepository;
import application.repository.UserRepository;
import application.service.businessRule.registerUser.RegisterUserCheck;
import application.service.exception.general.InvalidParamException;
import application.service.exception.general.ResourceNotFoundException;
import application.service.serviceAction.interfaceService.ExtendsUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;


import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class StudentService implements ExtendsUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private List<RegisterUserCheck> validationsRegisterUser; // Validações para cadastrar um usuário (student no caso).

    @Override
    @Cacheable(value = "studentsList")
    public List<StudentDto> findAll(String noClass) { // Método que retorna todos os alunos do sistema registrados, ou eu posso filtrar a busca (ALUNOS QUE NÃO TEM CLASSE ASSOCIADA).
        return verifyParameters(noClass);
    }

    @Override
    public StudentDto findById(Long id) { // Método retorna um aluno em específico do sistema.
        Student student = returnUser(id);
        return new StudentDto(student);
    }

    @Override
    @CacheEvict(value = {"studentsList", "usersList"}, allEntries = true)
    public StudentDto save(RegisterUserForm userForm) {       // Método cria (cadastra) um novo Estudante no banco de dados.

        validationsRegisterUser.forEach(v -> v.validation(userForm, userRepository)); // VALIDANDO!!

        Student student = new Student();
        convertFromFormToUser(student, userForm);
        Role role = roleRepository.findByName("ROLE_STUDENT").get();
        student.addRole(role);

        RegistrationVO registration = new RegistrationVO(Instant.now());
        student.setRegistration(registration);

        student = studentRepository.save(student);
        return new StudentDto(student);
    }

    @Override
    public Student returnUser(Long id) { // Método que retorna um estudante do banco.
        Optional<Student> student = studentRepository.findById(id);
        return student.orElseThrow(() -> new ResourceNotFoundException("Id : " + id + ", This student wasn't found on DataBase"));
    }

    @Override
    public List<StudentDto> verifyParameters(String noClass) { // Método que verifica os parametros passados na URL.
        List<Student> students;
        if (noClass != null) {
            if (noClass.equalsIgnoreCase("TRUE")) {
                students = studentRepository.findAllWhereClassRoomIsNull();
            } else if (noClass.equalsIgnoreCase("FALSE")) {
                students = studentRepository.findAll();
            } else {
                throw new InvalidParamException("This parameter : {" + noClass + "} is invalid.");
            }
        } else {
            students = studentRepository.findAll();
        }
        return convertToDto(students);
    }

    private List<StudentDto> convertToDto(List<Student> students) {  // Método que converte uma lista de Students para StudentsDto.
        return students.stream().map(StudentDto::new).collect(Collectors.toList());
    }

}
