package application.service.serviceLayer;

import application.dto.TeacherDto;
import application.entity.Role;
import application.entity.users.Teacher;
import application.form.RegisterUserForm;
import application.repository.RoleRepository;
import application.repository.TeacherRepository;
import application.repository.UserRepository;
import application.service.businessRule.RegisterUser.EmailAlreadyRegistered;
import application.service.businessRule.RegisterUser.RegisterUserCheck;
import application.service.exception.general.InvalidParamException;
import application.service.exception.general.ResourceNotFoundException;
import application.service.serviceLayer.interfacee.ExtendsUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
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

    @Override
    public List<TeacherDto> findAll(String noClass) {
        List<TeacherDto> teachersDtos =  verifyParameters(noClass);
        return teachersDtos;
    }

    @Override
    public TeacherDto findById(Long id) {
        Teacher teacher = returnUser(id);
        return new TeacherDto(teacher);
    }

    @Override
    public Teacher returnUser(Long id) {
        Optional<Teacher> teacher = teacherRepository.findById(id);
        return teacher.orElseThrow(()->new ResourceNotFoundException("Id : " + id + ", This teacher wasn't found on DataBase"));
    }


    @Override
    public TeacherDto save(RegisterUserForm userForm) {
        List<RegisterUserCheck> validations = Arrays.asList(new EmailAlreadyRegistered());

        validations.forEach(v->v.validation(userForm,userRepository));

        Teacher teacher = new Teacher();
        convertFromFormToUser(teacher, userForm);
        Role role = roleRepository.findByName("ROLE_TEACHER").get();
        teacher.addRole(role);
        teacher = teacherRepository.save(teacher);
        return new TeacherDto(teacher);
    }



    @Override
    public  List<TeacherDto> verifyParameters(String noClass) {

        List<Teacher> teachers = null;
        if (noClass != null) {
            if (noClass.toUpperCase().equals("TRUE")) {
              teachers = teacherRepository.findAllWhereClassRoomIsNull();
            } else if (noClass.toUpperCase().equals("FALSE")) {
                teachers = teacherRepository.findAll();
            } else {
                throw new InvalidParamException("This parameter : {" + noClass + "} is invalid");
            }
        } else {
            teachers = teacherRepository.findAll();
        }
        List<TeacherDto> teachersDtos = convertToDto(teachers);
        return teachersDtos;
    }

    private List<TeacherDto> convertToDto(List<Teacher> teachers) {
        return teachers.stream().map(TeacherDto::new).collect(Collectors.toList());
    }

}
