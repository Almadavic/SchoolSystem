package application.controllers.controllerLayer.authentication;

import javax.validation.Valid;

import application.forms.authenticationAuthorization.LoginForm;
import application.dtos.authenticationAuthorization.TokenDto;
import application.services.serviceLayer.AuthenticationService;
import application.services.serviceLayer.TokenService;
import application.services.exceptions.general.DatabaseException;
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
    private AuthenticationService authService;

    @PostMapping
    public ResponseEntity<TokenDto> authenticate(@RequestBody @Valid LoginForm form) {  // Método para fazer o login e se autenticar no sistema

        String token = authService.authenticate(form,authManager);

        return ResponseEntity.ok(new TokenDto(token, "Bearer"));
    }

}
