package application.service.serviceLayer;

import application.dto.StudentDto;
import application.entity.users.Student;
import application.entity.users.User;
import application.repository.StudentRepository;
import application.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public class StudentAreaService {
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private UserRepository userRepository;

    public StudentDto myData(Principal user) {
        String userName = user.getName();
        User userDataBase = userRepository.findByEmail(userName).get();               // Talvez dê pra refatorar com native query!
        Student student = studentRepository.findById(userDataBase.getId()).get();
        return new StudentDto(student);
    }
}
