package application.service.serviceLayer;

import application.dto.StudentDto;
import application.dto.TeacherDto;
import application.dto.UserDto;
import application.entity.Role;
import application.entity.users.Student;
import application.entity.users.Teacher;
import application.form.RegisterUserForm;
import application.repository.RoleRepository;
import application.repository.TeacherRepository;
import application.service.exception.general.InvalidParam;
import application.service.exception.general.ResourceNotFoundException;
import application.service.serviceLayer.interfacee.ExtendsUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

@Service
public class TeacherService  implements ExtendsUserService {

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Page<TeacherDto> findAll(Pageable pagination, String noClass) {
        Page<Teacher> teachers = null;
        if (noClass != null) {
            if (noClass.toUpperCase().equals("TRUE")) {
                // Ainda implementar isso, que é quando o cliente quer retornar todos os professsores que estão sem classe.
            } else if (noClass.toUpperCase().equals("FALSE")) {
                teachers = teacherRepository.findAll(pagination);
            } else {
                throw new InvalidParam("This parameter : {" + noClass + "} is invalid");
            }
        } else {
            teachers = teacherRepository.findAll(pagination);
        }

        Page<TeacherDto> teacherDtos = teachers.map(TeacherDto::new);
        return teacherDtos;
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
        convertFromFormToUser(teacher, userForm);
        Role role = roleRepository.findById(2l).get();
        teacher.addRole(role);
        teacher= teacherRepository.save(teacher);
        return new TeacherDto(teacher);
    }



}
