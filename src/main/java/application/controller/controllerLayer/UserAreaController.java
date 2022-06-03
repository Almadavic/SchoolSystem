package application.controller.controllerLayer;

import application.dto.UserDto;
import application.form.NewPasswordForm;
import application.service.serviceLayer.UserAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping(value = "/userarea")
public class UserAreaController { // Onde os professores e alunos tem acesso, fiz só 1 controle pois todos tem as mesmas informações

    @Autowired
    private UserAreaService userAreaService;

    @GetMapping(value = "/mydata")
    // Método para acessar as informações do proprio aluno logado.
    public ResponseEntity<? extends UserDto> myData(Principal user) {

        UserDto userDto =  userAreaService.myData(user);

        return ResponseEntity.ok().body( userDto);
    }

    @PutMapping(value = "/changepassword")
    // Método para alteração de senha.
    public ResponseEntity<String> changePassword(Principal user,@RequestBody @Valid NewPasswordForm newPasswordForm) {

        String message = userAreaService.changePassword(user,newPasswordForm);

        return ResponseEntity.ok().body(message);
    }
}

