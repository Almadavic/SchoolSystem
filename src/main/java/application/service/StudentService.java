package application.service;

import application.dto.StudentDto;
import application.entity.Student;
import application.entity.User;
import application.repository.StudentRepository;
import application.repository.UserRepository;
import application.service.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.security.Principal;
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

    public StudentDto myData(Principal user) {
        String userName = user.getName();
        User userDataBase = userRepository.findByEmail(userName).get();
        Student student = studentRepository.findById(userDataBase.getId()).get();
        return new StudentDto(student);
    }

    public StudentDto findByID(Long id) {
        Student student = errorNotFoundOrReturnEntity(id);
        return new StudentDto(student);
    }

    private Student errorNotFoundOrReturnEntity(Long id) {
        Optional<Student> student = studentRepository.findById(id);
        if (student.isEmpty()) {
            throw new ResourceNotFoundException("Student not found");
        }
        return student.get();
    }
}
