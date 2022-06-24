package application.service.serviceAction;

import application.dto.request.authenticationAuthorization.LoginForm;
import application.service.exception.general.DatabaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    private TokenService tokenService;


    public String authenticate(LoginForm form, AuthenticationManager authManager) { // Método para fazer o login e se autenticar no sistema.

        UsernamePasswordAuthenticationToken loginData = form.toConvert();   // converter os dados passado pelo usuario em um token de autenticação

        try {
            Authentication authentication = authManager.authenticate(loginData); // autenticar usuário com base nos dados informados por ele
            return tokenService.generateToken(authentication);      // geração do token .
        } catch (AuthenticationException e) {
            throw new DatabaseException("E-mail and / or password is / are wrong!");       // Causará um erro caso os dados passados pelo usuário estejam errados.
        }
    }
}
