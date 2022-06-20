package application.controllers.controllerLayer;

import application.dtos.UserDto;
import application.forms.NewPasswordForm;
import application.services.serviceLayer.UserAreaService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping(value = "/userarea")
public class UserAreaController { // Onde os professores, diretores e alunos tem acesso, fiz só 1 controle pois todos tem as mesmas informações.

    @Autowired
    private UserAreaService userAreaService;

    @GetMapping(value = "/mydata")
    @Operation(summary = "Acessar as informações do próprio usuário logado.")
    public ResponseEntity<? extends UserDto> myData(Principal user) {

        UserDto userDto = userAreaService.myData(user);

        return ResponseEntity.ok().body(userDto);
    }

    @PutMapping(value = "/changepassword")
    @Operation(summary = "Alterar a senha da conta.")
    public ResponseEntity<String> changePassword(Principal user, @RequestBody @Valid NewPasswordForm newPasswordForm) {

        String message = userAreaService.changePassword(user, newPasswordForm);

        return ResponseEntity.ok().body(message);
    }
}

