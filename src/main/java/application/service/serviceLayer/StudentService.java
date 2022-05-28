package application.service.serviceLayer;

import application.dto.StudentDto;

import application.entity.Role;
import application.entity.users.Student;
import application.entity.users.User;
import application.form.RegisterUserForm;
import application.repository.RoleRepository;
import application.repository.StudentRepository;
import application.service.exception.general.InvalidParam;
import application.service.exception.general.ResourceNotFoundException;
import application.service.serviceLayer.interfacee.ExtendsUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class StudentService  implements ExtendsUserService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public List<StudentDto> findAll(String noClass) {
        List<StudentDto> studentDtos = verifyParameters(noClass);
        return studentDtos;
    }

    @Override
    public StudentDto findById(Long id) {
        Student student = returnStudent(id);
        return new StudentDto(student);
    }

    @Override
    @CacheEvict(value = "studentsList")
    public StudentDto save(RegisterUserForm userForm) {

        Student student = new Student();
        convertFromFormToUser(student, userForm);               // Ajustar método, não deixar o banco salavar um usuário com mesmo email!
        Role role = roleRepository.findById(1l).get();
        student.addRole(role);
        student = studentRepository.save(student);
        return new StudentDto(student);
    }

    private Student returnStudent(Long id) {
        Optional<Student> student = studentRepository.findById(id);
        if (student.isEmpty()) {
            throw new ResourceNotFoundException("Id : " + id + ", This student wasn't found on DataBase");
        }
        return student.get();
    }

    @Override
    public List<StudentDto> verifyParameters(String noClass) {
        System.out.println(noClass);
        List<Student> students = null;
        if (noClass != null) {
            if (noClass.toUpperCase().equals("TRUE")) {
                students = studentRepository.findAllWhereClassRoomIsNull();
            } else if (noClass.toUpperCase().equals("FALSE")) {
                students = studentRepository.findAll();
            } else {
                throw new InvalidParam("This parameter : {" + noClass + "} is invalid.");
            }
        } else {
            students = studentRepository.findAll();
        }
        students.forEach(System.out::println);
        List<StudentDto> studentDtos = convertToDto(students);
        return studentDtos;
    }

    private List<StudentDto> convertToDto(List<Student> students) {
        return students.stream().map(StudentDto::new).collect(Collectors.toList());
    }

}
