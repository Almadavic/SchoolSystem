package application.services.businessRules.registerUser;

import application.entities.users.User;
import application.forms.RegisterUserForm;
import application.repositories.UserRepository;
import application.services.exceptions.general.EmailAlreadyRegisteredException;

import java.util.Optional;

public class EmailAlreadyRegistered implements RegisterUserCheck {

    @Override
    public void validation(RegisterUserForm userForm, UserRepository userRepository) {

        String email = userForm.getEmail();

        Optional<User> user = userRepository.findByEmail(email);

        if (user.isPresent()) {
            throw new EmailAlreadyRegisteredException("This e-mail already exists on system");
        }

    }
}
