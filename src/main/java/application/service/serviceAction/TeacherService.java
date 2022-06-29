package application.service.serviceAction;

import application.dto.response.TeacherDto;
import application.vo.RegistrationVO;
import application.entity.Role;
import application.entity.user.Teacher;
import application.dto.request.RegisterUserForm;
import application.repository.RoleRepository;
import application.repository.TeacherRepository;
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
public class TeacherService implements ExtendsUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private List<RegisterUserCheck> validationsRegisterUser; // Validações para cadastradar um usuário(teacher no caso).

    @Override
    @Cacheable(value = "teachersList")
    public List<TeacherDto> findAll(String noClass) { // Método que retorna todos os professores do sistema, ou eu posso filtrar a busca (PROFESSORES QUE NÃO TEM CLASSE ASSOCIADA).
        return verifyParameters(noClass);
    }

    @Override
    public TeacherDto findById(Long id) {  // Método que retorna um teacher em específico,passando o  id.
        Teacher teacher = returnUser(id);
        return new TeacherDto(teacher);
    }

    @Override
    @CacheEvict(value = {"teachersList", "usersList"}, allEntries = true)
    public TeacherDto save(RegisterUserForm userForm) {         // Método cria um novo Teacher (cadastra) no banco de dados.

        validationsRegisterUser.forEach(v -> v.validation(userForm, userRepository)); // VALIDANDO !!

        Teacher teacher = new Teacher();
        convertFromFormToUser(teacher, userForm);
        Role role = roleRepository.findByName("ROLE_TEACHER").get();
        teacher.addRole(role);

        RegistrationVO registration = new RegistrationVO(Instant.now());
        teacher.setRegistration(registration);

        teacher = teacherRepository.save(teacher);
        return new TeacherDto(teacher);
    }


    @Override
    public Teacher returnUser(Long id) {   // Método que retorna um professor do banco.
        Optional<Teacher> teacher = teacherRepository.findById(id);
        return teacher.orElseThrow(() -> new ResourceNotFoundException("Id : " + id + ", This teacher wasn't found on DataBase"));
    }


    @Override
    public List<TeacherDto> verifyParameters(String noClass) { // Método que verifica os parametros passados na URL.

        List<Teacher> teachers;
        if (noClass != null) {
            if (noClass.equalsIgnoreCase("TRUE")) {
                teachers = teacherRepository.findAllWhereClassRoomIsNull();
            } else if (noClass.equalsIgnoreCase("FALSE")) {
                teachers = teacherRepository.findAll();
            } else {
                throw new InvalidParamException("This parameter : {" + noClass + "} is invalid");
            }
        } else {
            teachers = teacherRepository.findAll();
        }
        return convertToDto(teachers);
    }

    private List<TeacherDto> convertToDto(List<Teacher> teachers) { // Método que converte uma lista de Teachers para TeachersDto.
        return teachers.stream().map(TeacherDto::new).collect(Collectors.toList());
    }

}
