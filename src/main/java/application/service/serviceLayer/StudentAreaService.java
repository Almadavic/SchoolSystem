package application.service.serviceLayer;

import application.dto.StudentDto;
import application.entity.users.Student;
import application.entity.users.User;
import application.form.NewPasswordForm;
import application.repository.StudentRepository;
import application.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.Principal;

@Service
public class StudentAreaService {
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private UserRepository userRepository;

    public StudentDto myData(Principal user) {
        User userDataBase = returnUser(user);              // Talvez dê pra refatorar com native query!
        Student student = studentRepository.findById(userDataBase.getId()).get();
        return new StudentDto(student);
    }


    public String changePassword(Principal user, NewPasswordForm newPasswordForm) {
        User userDataBase = returnUser(user);

        String newSenha = newPasswordForm.getNewPassword();
        String newSenhaEncoder = new BCryptPasswordEncoder().encode(newSenha); // Método ainda não funcional

        userDataBase.setPassword(newSenhaEncoder);

        userRepository.save(userDataBase);

        return "Your new password is : "+newSenha;
    }

    private User returnUser(Principal user) {
        String userName = user.getName();
        User userDataBase = userRepository.findByEmail(userName).get();
        return userDataBase;
    }
}
