package application.service.businessRule.registerUser;

import application.dto.request.RegisterUserForm;
import application.repository.UserRepository;

public interface RegisterUserCheck { // Interface de regras de negócio relacionada ao registrar um usuário no sistema.

    void validation(RegisterUserForm userForm, UserRepository userRepository); // Validação!
}
