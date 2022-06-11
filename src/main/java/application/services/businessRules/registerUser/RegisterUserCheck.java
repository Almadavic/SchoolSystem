package application.services.businessRules.registerUser;

import application.forms.RegisterUserForm;
import application.repositories.UserRepository;

public interface RegisterUserCheck { // Interface de regras de negócio relacionada ao registrar um usuário no sistema.

    void validation(RegisterUserForm userForm, UserRepository userRepository); // Validação!
}
