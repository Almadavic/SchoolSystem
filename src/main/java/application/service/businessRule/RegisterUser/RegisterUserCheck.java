package application.service.businessRule.RegisterUser;

import application.form.RegisterUserForm;
import application.repository.UserRepository;

public interface RegisterUserCheck {

    void validation(RegisterUserForm userForm ,UserRepository userRepository);
}
