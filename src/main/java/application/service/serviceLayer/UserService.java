package application.service.serviceLayer;

import application.dto.UserDto;
import application.entity.users.Teacher;
import application.entity.users.User;
import application.repository.UserRepository;
import application.service.exception.general.InvalidParam;
import application.service.exception.general.ResourceNotFoundException;
import application.service.serviceLayer.interfacee.AllUserTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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
    public List<UserDto> findAll(String rolesName) {
        List<UserDto> userDtos = verifyParameters(rolesName);
        return userDtos;
    }

    @Override
    public UserDto findById(Long id) {
        User user = returnUser(id);
        return new UserDto(user);
    }

    public String remove(Long id) { // Esse método serve tanto para teacher como estudante. //

        User user = returnUser(id);
        String userInfo = user.toString();
        verifyInstance(user);
        userRepository.delete(user);

        return "User  : " + userInfo + " removed with success!";
    }

    private User returnUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new ResourceNotFoundException("Id : " + id + ", This student wasn't found on DataBase");
        }
        return user.get();
    }

    @Override
    public List<UserDto> verifyParameters(String rolesName) {
        String rolesNameOriginal = rolesName;
        List<User> users;
        if (rolesName != null) {
            rolesName = "ROLE_" + rolesName.toUpperCase();
            if (rolesName.equals("ROLE_TEACHER") || rolesName.equals("ROLE_STUDENT")) {
                users = userRepository.findByRolesName(rolesName);
            } else {
                throw new InvalidParam("This parameter : {" + rolesNameOriginal + "} is invalid");
            }
        } else {
            users = userRepository.findAll();
        }
        List<UserDto> userDtos = convertToDto(users);
        return userDtos;
    }

    private List<UserDto> convertToDto(List<? extends User> users) {
        return users.stream().map(UserDto::new).collect(Collectors.toList());
    }

    private void verifyInstance(User user) { // Tive que fazer só para professor por causa da relação no banco de dados!
        if (user instanceof Teacher) {
            Teacher teacher = (Teacher) user;
            if (teacher.getClassRoom() != null) {
                teacher.getClassRoom().setTeacher(null);
                teacher.setClassRoom(null);
            }
        }
    }

}
