package application.service.serviceLayer;

import application.dto.UserDto;
import application.entity.Address;
import application.entity.users.Student;
import application.entity.users.Teacher;
import application.entity.users.User;
import application.form.RegisterAddressForm;
import application.form.RegisterUserForm;
import application.repository.UserRepository;
import application.service.exception.general.InvalidParam;
import application.service.exception.general.ResourceNotFoundException;
import application.service.serviceLayer.interfacee.AllUserTypeService;
import application.service.serviceLayer.interfacee.GenericMethodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService, AllUserTypeService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException { // Método que mostra pro spring security como será feita a autenticação.
        Optional<User> user = userRepository.findByEmail(username);
        if (user.isPresent()) {
            return user.get();
        }
        throw new UsernameNotFoundException("Invalid data!");
    }


    @Override
    public Page<UserDto> findAll(Pageable pagination, String rolesName) {
        String rolesNameOriginal = rolesName;
        Page<User> users;

        if (rolesName != null) {
            rolesName = "ROLE_" + rolesName.toUpperCase();
            if (rolesName.equals("ROLE_TEACHER") || rolesName.equals("ROLE_STUDENT")) {

                users = userRepository.findByRolesName(pagination, rolesName);
            } else {
                throw new InvalidParam("This parameter : {" + rolesNameOriginal + "} is invalid");
            }
        } else {
            users = userRepository.findAll(pagination);
        }

        Page<UserDto> usersDto = users.map(UserDto::new);
        return usersDto;
    }

    @Override
    public UserDto findById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new ResourceNotFoundException("Id : " + id + ", This user wasn't found on DataBase");
        }
        UserDto userDto = new UserDto(user.get());
        return userDto;
    }
    
}
