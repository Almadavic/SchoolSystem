package application.service.serviceLayer;

import application.dto.StudentDto;

import application.entity.Registration;
import application.entity.Role;
import application.entity.users.Student;
import application.form.RegisterUserForm;
import application.repository.RoleRepository;
import application.repository.StudentRepository;
import application.repository.UserRepository;
import application.service.businessRule.RegisterUser.EmailAlreadyRegistered;
import application.service.businessRule.RegisterUser.RegisterUserCheck;
import application.service.exception.general.InvalidParamException;
import application.service.exception.general.ResourceNotFoundException;
import application.service.serviceLayer.interfacee.ExtendsUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;


import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class StudentService  implements ExtendsUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Override
    @Cacheable(value = "studentsList")
    public List<StudentDto> findAll(String noClass) {
        List<StudentDto> studentDtos = verifyParameters(noClass);
        return studentDtos;
    }

    @Override
    public StudentDto findById(Long id) {
        Student student = returnUser(id);
        return new StudentDto(student);
    }

    @Override
    @CacheEvict(value = {"studentsList","usersList"},allEntries = true)
    public StudentDto save(RegisterUserForm userForm) {
        List<RegisterUserCheck> validations = Arrays.asList(new EmailAlreadyRegistered());
        validations.forEach(v -> v.validation(userForm, userRepository));
        Student student = new Student();
        convertFromFormToUser(student, userForm);
        Role role = roleRepository.findByName("ROLE_STUDENT").get();
        student.addRole(role);

        Registration registration = new Registration(Instant.now(),student);
        student.setRegistration(registration);

        student = userRepository.save(student);
        return new StudentDto(student);
    }

    @Override
    public Student returnUser(Long id) {
        Optional<Student> student = studentRepository.findById(id);
        return student.orElseThrow(()->new ResourceNotFoundException("Id : " + id + ", This teacher wasn't found on DataBase"));
    }

    @Override
    public List<StudentDto> verifyParameters(String noClass) {
        List<Student> students = null;
        if (noClass != null) {
            if (noClass.toUpperCase().equals("TRUE")) {
                students = studentRepository.findAllWhereClassRoomIsNull();
            } else if (noClass.toUpperCase().equals("FALSE")) {
                students = studentRepository.findAll();
            } else {
                throw new InvalidParamException("This parameter : {" + noClass + "} is invalid.");
            }
        } else {
            students = studentRepository.findAll();
        }
        List<StudentDto> studentDtos = convertToDto(students);
        return studentDtos;
    }

    private List<StudentDto> convertToDto(List<Student> students) {
        return students.stream().map(StudentDto::new).collect(Collectors.toList());
    }

}
