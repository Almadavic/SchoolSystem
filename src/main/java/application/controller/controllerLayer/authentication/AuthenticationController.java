package application.controller.controllerLayer.authentication;

import javax.validation.Valid;

import application.form.authenticationAuthorization.LoginForm;
import application.dto.authenticationAuthorization.TokenDto;
import application.service.serviceLayer.TokenService;
import application.service.exception.general.DatabaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController   // Identificando  que é um rest-controller
@RequestMapping(value = "/auth")   // Recurso para "encontrar" esse controller
@Profile(value = {"prod", "test"})     // Essa classe só será chamada nos perfis de prod e test
public class AuthenticationController {         // Controller para fazer a autenticação
    @Autowired
    private AuthenticationManager authManager;    // Injeção de dependencia automatica - > AuthenticationManager
    @Autowired
    private TokenService tokenService; // Injeção de dependencia automatica - > TokenService

    @PostMapping
    public ResponseEntity<TokenDto> authenticate(@RequestBody @Valid LoginForm form) {  // Método para fazer o login e se autenticar no sistema
        UsernamePasswordAuthenticationToken loginData = form.toConvert();   // converter os dados passado pelo usuario em um token de autenticação

        try {
            Authentication authentication = authManager.authenticate(loginData); // autenticar usuário com base nos dados informados por ele
            String token = tokenService.generateToken(authentication);      // geração do token .
            return ResponseEntity.ok(new TokenDto(token, "Bearer"));  // Devolvendo o token pro cliente e o seu tipo.
        } catch (AuthenticationException e) {
            throw new DatabaseException("E-mail and / or password is wrong!");       // Causará um erro caso os dados passados pelo usuário estejam errados.
        }
    }

}
