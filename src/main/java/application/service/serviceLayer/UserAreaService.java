package application.service.serviceLayer;

import application.dto.PrincipalDto;
import application.dto.StudentDto;
import application.dto.TeacherDto;
import application.dto.UserDto;
import application.entity.users.Student;
import application.entity.users.Teacher;
import application.entity.users.User;
import application.form.NewPasswordForm;
import application.repository.UserRepository;
import application.service.exception.studentAreaService.SamePasswordException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public class UserAreaService {
    @Autowired
    private UserRepository userRepository;

    public UserDto myData(Principal user) {

          User userDataBase =  returnUser(user);
          if(userDataBase instanceof Teacher) {
              return new TeacherDto(userDataBase);
          } else if( userDataBase instanceof Student) {
              return new StudentDto(userDataBase);
          } else {                             // Esse Else é referente ao diretor! Se houvesse mais cargos futuramente, poderia ter conflito. Mas só vão ter 3 cargos.
              return new PrincipalDto(userDataBase);
          }
    }

    public String changePassword(Principal user, NewPasswordForm newPasswordForm) { // Posso fazer uma lista de SOLID AQUI DENTRO!, checkPassword sendo uma das regras de negocio, e implementar mais!

        User userDataBase = returnUser(user);
        String newPassword = newPasswordForm.getNewPassword();
        String oldPassword = userDataBase.getPassword();
        String newPasswordEncoder = new BCryptPasswordEncoder().encode(newPassword);
        System.out.println("------------------------------------VELHA : " + oldPassword);
        System.out.println("-----------------------------------NOVA : " + newPasswordEncoder);
        checkPasswords(newPasswordEncoder, oldPassword); // MÉTODO de verificar se senha é igual, ainda NÃO FUNCIONA!
        userDataBase.setPassword(newPasswordEncoder);
        userRepository.save(userDataBase);

        return "SUCCESS! You just changed your password!";
    }


    private void checkPasswords(String oldPassword, String newPassword) {     // MÉTODO de verificar se senha é igual, ainda NÃO FUNCIONA! //

        if (oldPassword.equals(newPassword)) {
            throw new SamePasswordException("Your new password can't be equal the last one");
        }
    }


    private User returnUser(Principal user) {                                 // Método que retorna um usuário
        User userDataBase = userRepository.findByEmail(user.getName()).get();
        return userDataBase;
    }

}
