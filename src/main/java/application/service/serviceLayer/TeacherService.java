package application.service.serviceLayer;

import application.dto.StudentDto;
import application.dto.TeacherDto;
import application.dto.UserDto;
import application.entity.Role;
import application.entity.users.Student;
import application.entity.users.Teacher;
import application.entity.users.User;
import application.form.RegisterUserForm;
import application.repository.RoleRepository;
import application.repository.TeacherRepository;
import application.service.exception.general.InvalidParam;
import application.service.exception.general.ResourceNotFoundException;
import application.service.serviceLayer.interfacee.ExtendsUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TeacherService implements ExtendsUserService {

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public List<TeacherDto> findAll(String noClass) {
        List<TeacherDto> teachersDtos =  verifyParameters(noClass);
        return teachersDtos;
    }

    @Override
    public TeacherDto findById(Long id) {
        Teacher teacher = returnTeacher(id);
        return new TeacherDto(teacher);
    }

    private Teacher returnTeacher(Long id) {
        Optional<Teacher> teacher = teacherRepository.findById(id);
        if (teacher.isEmpty()) {
            throw new ResourceNotFoundException("Id : " + id + ", This teacher wasn't found on DataBase");
        }
        return teacher.get();
    }


    @Override
    public TeacherDto save(RegisterUserForm userForm) {
        Teacher teacher = new Teacher();
        convertFromFormToUser(teacher, userForm);           // Ajustar método, não deixar o banco salavar um usuário com mesmo email!
        Role role = roleRepository.findById(2l).get();
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
                throw new InvalidParam("This parameter : {" + noClass + "} is invalid");
            }
        } else {
            teachers = teacherRepository.findAll();
        }
        List<TeacherDto> teachersDtos = convertToDto(teachers);
        return teachersDtos;
    }

    private List<TeacherDto> convertToDto(List<? extends User> teachers) {
        return teachers.stream().map(TeacherDto::new).collect(Collectors.toList());
    }

}
