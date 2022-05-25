package application.service.serviceLayer;

import application.dto.StudentDto;
import application.entity.users.Student;
import application.entity.users.User;
import application.form.NewPasswordForm;
import application.repository.StudentRepository;
import application.repository.UserRepository;
import application.service.exception.studentArea.SamePassword;
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
        String newPassword = newPasswordForm.getNewPassword();
        String oldPassword = userDataBase.getPassword();
        String newPasswordEncoder = new BCryptPasswordEncoder().encode(newPassword);
        checkPasswords(newPassword, oldPassword); // MÉTODO de verificar se senha é igual ainda NÃO FUNCIONA!
        userDataBase.setPassword(newPasswordEncoder);
        userDataBase = userRepository.save(userDataBase);

        return "SUCCESS! You just changed your password!";
    }

    private User returnUser(Principal user) {      // Método retorna um usuário,  Criei esse método paranão ter que ficar repetindo o mesmo código na classse.
        String userName = user.getName();
        User userDataBase = userRepository.findByEmail(userName).get();
        return userDataBase;
    }

    private void checkPasswords(String oldPassword, String newPassword) {     // MÉTODO de verificar se senha é igual ainda NÃO FUNCIONA!
        if (oldPassword.equals(newPassword)) {
            throw new SamePassword("Your new password can't be equal the last one");
        }
    }
}
