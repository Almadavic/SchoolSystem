package application.service.businessRule.removeUser;

import application.domain.entity.user.Principal;
import application.domain.entity.user.User;
import application.service.exception.userService.RemovePrincipalException;
import org.springframework.stereotype.Component;

@Component
public class RemovePrincipal implements RemoveUserCheck {

    @Override
    public void validation(User user) {
        if (user instanceof Principal) {
            throw new RemovePrincipalException("You can't remove a principal from the system.");
        }
    }
}
