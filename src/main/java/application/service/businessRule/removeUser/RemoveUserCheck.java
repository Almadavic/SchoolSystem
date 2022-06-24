package application.service.businessRule.removeUser;

import application.domain.entity.user.User;

public interface RemoveUserCheck {

    void validation(User user);
}
