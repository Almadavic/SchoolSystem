package application.service.serviceLayer;

import application.dto.StudentDto;
import application.entity.users.Student;
import application.repository.StudentRepository;
import application.repository.UserRepository;
import application.service.exception.database.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private UserRepository userRepository;

    public Page<StudentDto> findAll(Pageable pagination) {
        Page<Student> students = studentRepository.findAll(pagination);
        Page<StudentDto> studentDtos = students.map(StudentDto::new);
        return studentDtos;
    }

    public StudentDto findById(Long id) {
        Student student = returnStudent(id);
        return new StudentDto(student);
    }

    private Student returnStudent(Long id) {
        Optional<Student> student = studentRepository.findById(id);
        if (student.isEmpty()) {
            throw new ResourceNotFoundException(id+", This student wasn't found on DataBase");
        }
        return student.get();
    }
}
