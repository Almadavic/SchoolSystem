package application.services.serviceLayer;

import application.dtos.PrincipalDto;
import application.dtos.StudentDto;
import application.dtos.TeacherDto;
import application.dtos.UserDto;
import application.entities.users.Student;
import application.entities.users.Teacher;
import application.entities.users.User;
import application.forms.NewPasswordForm;
import application.repositories.UserRepository;
import application.services.businessRules.ChangePassword.ChangePasswordCheck;
import application.services.businessRules.ChangePassword.SamePassword;
import application.services.businessRules.ChangePassword.ShortPassword;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;

@Service
public class UserAreaService {
    @Autowired
    private UserRepository userRepository;

    public UserDto myData(Principal user) {

          User userEntity =  returnUser(user);
          if(userEntity instanceof Teacher) {
              return new TeacherDto(userEntity);
          } else if( userEntity instanceof Student) {
              return new StudentDto(userEntity);
          } else if( userEntity instanceof Principal){
              return new PrincipalDto(userEntity);
          } else {
              return null; // Isso nunca aconteceria, pois só existe instancias de teacher,student e principal. Mas eu fiz a lógica dessa maneira pois futuramente poderia haver outro cargo, assim, sendo melhor para a manutenção!
          }
    }

    public String changePassword(Principal user, NewPasswordForm newPasswordForm) {

        List<ChangePasswordCheck> validations = Arrays.asList(new SamePassword(), new ShortPassword());

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
        User userDataBase = userRepository.findByEmail(user.getName()).get(); // Não retorna um Optional pois não tem como estar vazio!
        return userDataBase;
    }

}
