package application.service.serviceLayer;

import application.dto.UserDto;
import application.entity.users.Teacher;
import application.entity.users.User;
import application.repository.UserRepository;
import application.service.exception.general.InvalidParamException;
import application.service.exception.general.ResourceNotFoundException;
import application.service.serviceLayer.interfacee.AllUserTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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
    @Cacheable(value = "usersList")
    public List<UserDto> findAll(String rolesName) {
        List<UserDto> usersDto = verifyParameters(rolesName);
        return usersDto;
    }

    @Override
    public UserDto findById(Long id) {
        User user = returnUser(id);
        return new UserDto(user);
    }

    @CacheEvict(value = {"usersList","teachersList","studentsList","classesRoomlist","studentsListByClassRoom"}, allEntries = true)
    public String remove(Long id) { // Esse método serve tanto para teacher como estudante. //

        User user = returnUser(id);
        String userInfo = user.toString();
        verifyInstance(user);
        userRepository.delete(user);

        return "User  : " + userInfo + " removed with success!";
    }
    @Override
    public User returnUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElseThrow(()->new ResourceNotFoundException("Id : " + id + ", This teacher wasn't found on DataBase"));
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
                throw new InvalidParamException("This parameter : {" + rolesNameOriginal + "} is invalid");
            }
        } else {
            users = userRepository.findAll();
        }
        List<UserDto> usersDto = convertToDto(users);
        return usersDto;
    }

    public List<UserDto> convertToDto(List<User> users) {          // Se possível, colocar esse método na interface! Para todos que herdam usuário terem.
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
