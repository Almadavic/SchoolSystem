package application.services.businessRules.RegisterUser;

import application.forms.RegisterUserForm;
import application.repositories.UserRepository;

public interface RegisterUserCheck {

    void validation(RegisterUserForm userForm ,UserRepository userRepository);
}
