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
import application.service.businessRule.ChangePassword.ChangePasswordCheck;
import application.service.businessRule.ChangePassword.SamePassword;
import application.service.businessRule.ChangePassword.ShortPassword;
import application.service.exception.studentAreaService.SamePasswordException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    public String changePassword(Principal user, NewPasswordForm newPasswordForm) { // Posso fazer uma lista de SOLID AQUI DENTRO!, checkPassword sendo uma das regras de negocio, e implementar mais!Li

        List<ChangePasswordCheck> validations = Arrays.asList(new SamePassword(),new ShortPassword());

        User userDataBase = returnUser(user);
        String newPassword = newPasswordForm.getNewPassword();
        String oldPassword = userDataBase.getPassword();

        validations.forEach(v -> v.validation(newPassword,oldPassword));

        String newPasswordEncoded = new BCryptPasswordEncoder().encode(newPassword);

        userDataBase.setPassword(newPasswordEncoded);
        userRepository.save(userDataBase);

        return "SUCCESS! You just changed your password!";
    }


    private User returnUser(Principal user) {                                 // Método que retorna um usuário
        User userDataBase = userRepository.findByEmail(user.getName()).get(); // Não retorna um Optinal pois não tem como estar vazio!
        return userDataBase;
    }

}
