package application.services.serviceLayer;

import application.dtos.authenticationAuthorization.TokenDto;
import application.forms.authenticationAuthorization.LoginForm;
import application.services.exceptions.general.DatabaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    private TokenService tokenService;


    public String authenticate(LoginForm form, AuthenticationManager authManager) {

        UsernamePasswordAuthenticationToken loginData = form.toConvert();   // converter os dados passado pelo usuario em um token de autenticação

        try {
            Authentication authentication = authManager.authenticate(loginData); // autenticar usuário com base nos dados informados por ele
            String token = tokenService.generateToken(authentication);      // geração do token .
            return token;
        } catch (AuthenticationException e) {
            throw new DatabaseException("E-mail and / or password is wrong!");       // Causará um erro caso os dados passados pelo usuário estejam errados.
        }
    }
}
