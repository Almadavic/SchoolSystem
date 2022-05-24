package application.service.serviceLayer;

import application.dto.UserDto;
import application.entity.users.User;
import application.repository.UserRepository;
import application.service.exception.database.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(username);
        if (user.isPresent()) {
            return  user.get();
        }
        throw new UsernameNotFoundException("Invalid data!");
    }

    public Page<UserDto> findAll(Pageable pagination,String rolesName) {
        Page<User> users;
        if(rolesName!=null) {
            rolesName = "ROLE_"+rolesName.toUpperCase();
            users = userRepository.findByRolesName(pagination,rolesName);
        } else {
             users = userRepository.findAll(pagination);
        }
        Page<UserDto> usersDto = users.map(UserDto::new);
        return usersDto;
    }

    public UserDto findById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty()) {
            throw new ResourceNotFoundException("User not found");
        }
        UserDto userDto = new UserDto(user.get());
        return userDto;
    }

}
