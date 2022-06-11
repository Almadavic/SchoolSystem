package application.services.businessRules.removeUser;

import application.entities.users.User;

public interface RemoveUserCheck {

    void validation(User user);
}
