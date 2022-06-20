package application.controllers.controllerLayer.authentication;

import javax.validation.Valid;

import application.forms.authenticationAuthorization.LoginForm;
import application.dtos.authenticationAuthorization.TokenDto;
import application.services.serviceLayer.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
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
    private AuthenticationService authService;  // Injeção de dependencia automatica - > AuthenticationService

    @PostMapping
    @Operation(summary = "Faz o login e se autentica no sistema.")
    public ResponseEntity<TokenDto> authenticate(@RequestBody @Valid LoginForm form) {

        String token = authService.authenticate(form, authManager); // Hash do Token que será retornado para o Client.

        return ResponseEntity.ok(new TokenDto(token, "Bearer"));
    }

}
