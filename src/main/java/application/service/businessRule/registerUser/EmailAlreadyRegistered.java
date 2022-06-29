package application.service.businessRule.registerUser;

import application.entity.user.User;
import application.dto.request.RegisterUserForm;
import application.repository.UserRepository;
import application.service.exception.general.EmailAlreadyRegisteredException;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Order(1)
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
