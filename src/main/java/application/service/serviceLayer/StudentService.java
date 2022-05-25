package application.service.serviceLayer;

import application.dto.StudentDto;
import application.entity.users.Student;
import application.repository.StudentRepository;
import application.repository.UserRepository;
import application.service.exception.general.InvalidParam;
import application.service.exception.general.ResourceNotFoundException;
import application.service.serviceLayer.interfacee.ExtendsUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class StudentService implements ExtendsUserService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Page<StudentDto> findAll(Pageable pagination, String noClass) {
        Page<Student> students = null;
        if (noClass != null) {
            if (noClass.toUpperCase().equals("TRUE")) {
                // Ainda implementar isso, que é quando o cliente quer retornar todos os alunos que estão sem classe.
            } else if (noClass.toUpperCase().equals("FALSE")) {
                students = studentRepository.findAll(pagination);
            } else {
                throw new InvalidParam("This parameter : {" + noClass + "} is invalid");
            }
        } else {
            students = studentRepository.findAll(pagination);
        }

        Page<StudentDto> studentDtos = students.map(StudentDto::new);
        return studentDtos;
    }

    @Override
    public StudentDto findById(Long id) {
        Student student = returnStudent(id);
        return new StudentDto(student);
    }

    private Student returnStudent(Long id) {
        Optional<Student> student = studentRepository.findById(id);
        if (student.isEmpty()) {
            throw new ResourceNotFoundException("Id : " + id + ", This student wasn't found on DataBase");
        }
        return student.get();
    }
}
