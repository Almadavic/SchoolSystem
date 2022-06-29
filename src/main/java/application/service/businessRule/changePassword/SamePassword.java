package application.service.businessRule.changePassword;

import application.service.exception.studentAreaService.SamePasswordException;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class SamePassword implements ChangePasswordCheck {

    @Override
    public void validation(String newPassword, String oldPassword) {     // MÉTODO de verificar se senha é igual, ainda NÃO FUNCIONA! //

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        boolean check = passwordEncoder.matches(newPassword, oldPassword);
        if (check) {
            throw new SamePasswordException("Your new password can't be equal the last one");
        }
    }
}
