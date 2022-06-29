package application.service.serviceAction;

import application.dto.response.PrincipalDto;
import application.dto.response.StudentDto;
import application.dto.response.TeacherDto;
import application.dto.response.UserDto;
import application.entity.user.Student;
import application.entity.user.Teacher;
import application.entity.user.User;
import application.dto.request.NewPasswordForm;
import application.repository.UserRepository;
import application.service.businessRule.changePassword.ChangePasswordCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
public class UserAreaService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private List<ChangePasswordCheck> validationsChangePassword; // Validações para a nova senha.

    public UserDto myData(Principal user) { // Método para acessar as informações do próprio usuário logado.

        User userEntity = returnUser(user);
        if (userEntity instanceof Teacher) {
            return new TeacherDto(userEntity);
        } else if (userEntity instanceof Student) {
            return new StudentDto(userEntity);
        } else if (userEntity instanceof application.entity.user.Principal) {
            return new PrincipalDto(userEntity);
        } else {
            return null; // Isso nunca aconteceria, pois só existe instancias de teacher,student e principal. Mas eu fiz a lógica dessa maneira pois futuramente poderia haver outro cargo, assim, sendo melhor para a manutenção!
        }
    }

    public String changePassword(Principal user, NewPasswordForm newPasswordForm) { // Método para alterar de senha.

        User userDataBase = returnUser(user);
        String newPassword = newPasswordForm.getNewPassword();
        String oldPassword = userDataBase.getPassword();

        validationsChangePassword.forEach(v -> v.validation(newPassword, oldPassword)); // VALIDANDO !!

        String newPasswordEncoded = new BCryptPasswordEncoder().encode(newPassword);

        userDataBase.setPassword(newPasswordEncoded);
        userRepository.save(userDataBase);

        return "SUCCESS! You just changed your password!"; // Caso a senha seja alterada com sucesso, irá aparecer isso para o usuário.
    }


    private User returnUser(Principal user) {                                 // Método que retorna um usuário do banco.
        return userRepository.findByEmail(user.getName()).get(); // Não retorna um Optional pois não tem como estar vazio!
    }

}
