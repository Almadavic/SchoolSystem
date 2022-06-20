package application.services.businessRules.removeUser;

import application.entities.users.Principal;
import application.entities.users.User;
import application.services.exceptions.userService.RemovePrincipalException;
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
