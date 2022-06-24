package application.service.businessRule.changePassword;

import application.service.exception.studentAreaService.ShortPasswordException;
import org.springframework.stereotype.Component;

@Component
public class ShortPassword implements ChangePasswordCheck {

    @Override
    public void validation(String newPassword, String oldPassword) {
        oldPassword = null; // Só para provar que não tem utilidade

        if (newPassword.length() < 6) {
            throw new ShortPasswordException("Your password cannot be smaller than 6 characters");
        }

    }
}
