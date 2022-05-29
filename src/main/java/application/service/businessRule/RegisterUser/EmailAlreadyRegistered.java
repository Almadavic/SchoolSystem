package application.service.businessRule.RegisterUser;

import application.entity.users.User;
import application.form.RegisterUserForm;
import application.repository.UserRepository;
import application.service.exception.usersService.EmailAlreadyRegisteredException;

import java.util.Optional;

public class EmailAlreadyRegistered implements RegisterUserCheck{

    @Override
    public void validation(RegisterUserForm userForm , UserRepository userRepository) {

        String email = userForm.getEmail();

        Optional<User> user = userRepository.findByEmail(email);

        if(user.isPresent()) {
            throw new EmailAlreadyRegisteredException("This e-mail already exists on system");
        }

    }
}
