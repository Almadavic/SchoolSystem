package application.services.serviceLayer;

import application.dtos.UserDto;
import application.entities.users.Teacher;
import application.entities.users.User;
import application.repositories.UserRepository;
import application.services.businessRules.removeUser.RemovePrincipal;
import application.services.businessRules.removeUser.RemoveUserCheck;
import application.services.exceptions.general.InvalidParamException;
import application.services.exceptions.general.ResourceNotFoundException;
import application.services.serviceLayer.interfaces.AllUserTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService, AllUserTypeService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException { // Método que mostra pro spring security como será feita a autenticação.
        Optional<User> user = userRepository.findByEmail(username);
        return user.get();
    }


    @Override
    @Cacheable(value = "usersList")
    public List<UserDto> findAll(String rolesName) {  // Método retorna todos os usuários do sistema, ou eu posso filtrar a busca (PELA ROLE),ex : todos os usuarios que tenham a role professor.
        return verifyParameters(rolesName);
    }

    @Override
    public UserDto findById(Long id) {  // Método que me retorna um User específico do sistema, não importa a role.
        User user = returnUser(id);
        return new UserDto(user);
    }

    @CacheEvict(value = {"usersList", "teachersList", "studentsList", "classesRoomlist", "studentsListByClassRoom"}, allEntries = true)
    public String remove(Long id) { // Método remove um usuário.
        List<RemoveUserCheck> validations = Arrays.asList(new RemovePrincipal()); // Validação para remover um usuário.

        User user = returnUser(id);

        validations.forEach(v -> v.validation(user));

        String userInfo = user.toString();
        verifyInstance(user);
        userRepository.delete(user);

        return "User  : " + userInfo + " removed with success!";
    }

    @Override
    public User returnUser(Long id) { // Método que retorna um usuário do sistema.
        Optional<User> user = userRepository.findById(id);
        return user.orElseThrow(() -> new ResourceNotFoundException("Id : " + id + ", This teacher wasn't found on DataBase"));
    }

    @Override
    public List<UserDto> verifyParameters(String rolesName) { // Método que verifica os parametros passados na URL.
        String rolesNameOriginal = rolesName;
        List<User> users;
        if (rolesName != null) {
            rolesName = "ROLE_" + rolesName.toUpperCase();
            if (rolesName.equals("ROLE_TEACHER") || rolesName.equals("ROLE_STUDENT")) {
                users = userRepository.findByRolesName(rolesName);
            } else {
                throw new InvalidParamException("This parameter : {" + rolesNameOriginal + "} is invalid");
            }
        } else {
            users = userRepository.findAll();
        }
        return convertToDto(users);
    }

    public List<UserDto> convertToDto(List<User> users) {     // Método que converte uma lista de Users para UsersDto.
        return users.stream().map(UserDto::new).collect(Collectors.toList());
    }

    private void verifyInstance(User user) { // Tive que fazer só para a classe professor por causa da relação no banco de dados!
        if (user instanceof Teacher) {
            Teacher teacher = (Teacher) user;
            if (teacher.getClassRoom() != null) {
                teacher.getClassRoom().setTeacher(null);
                teacher.setClassRoom(null);
            }
        }
    }

}
