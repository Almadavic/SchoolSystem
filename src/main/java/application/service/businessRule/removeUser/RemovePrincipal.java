package application.service.businessRule.removeUser;

import application.entity.user.Principal;
import application.entity.user.User;
import application.service.exception.userService.RemovePrincipalException;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class RemovePrincipal implements RemoveUserCheck {

    @Override
    public void validation(User user) {
        if (user instanceof Principal) {
            throw new RemovePrincipalException("You can't remove a principal from the system.");
        }
    }
}
