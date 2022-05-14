package application.service;

import application.dto.UserDto;
import application.entity.User;
import application.repository.UserRepository;
import application.service.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = repository.findByEmail(username);
        if (user.isPresent()) {
            return  user.get();
        }
        throw new UsernameNotFoundException("Dados inválidos!");
    }

    public Page<UserDto> findAll(Pageable pagination,String rolesNome) {
        Page<User> users;
        rolesNome = "ROLE_"+rolesNome.toUpperCase();
        if(rolesNome!=null) {
            users = repository.findByRolesName(pagination,rolesNome);

        } else {
             users = repository.findAll(pagination);
        }
        Page<UserDto> usersDto = users.map(UserDto::new);
        return usersDto;
    }

    public UserDto findById(Long id) {
        Optional<User> user = repository.findById(id);
        if(user.isEmpty()) {
            throw new ResourceNotFoundException("User not found");
        }
        UserDto userDto = new UserDto(user.get());
        return userDto;
    }

}
