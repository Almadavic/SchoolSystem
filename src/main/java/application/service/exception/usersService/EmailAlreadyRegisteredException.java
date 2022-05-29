package application.service.exception.usersService;

import application.service.businessRule.RegisterUser.EmailAlreadyRegistered;

import java.io.Serial;

public class EmailAlreadyRegisteredException extends RuntimeException{

    @Serial
    private static final long serialVersionUID = 1L;

    public EmailAlreadyRegisteredException(String msg) {
        super(msg);
    }
}
