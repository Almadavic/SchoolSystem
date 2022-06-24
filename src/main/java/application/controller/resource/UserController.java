package application.controller.resource;

import application.controller.resource.interfaceController.AllUserTypeController;
import application.dto.response.UserDto;
import application.service.serviceAction.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/users")
public class UserController implements AllUserTypeController { // Controller para verificar todos os usuários do sistema. Apenas o diretor tem acesso aqui! Para fazer controle.

    @Autowired
    private UserService userService;

    @Override
    @GetMapping
    @Operation(summary = "Me retorna todos os usuários do sistema, ou eu posso filtrar a busca (PELA ROLE),ex : todos os usuarios que tenham a role professor.")
    public ResponseEntity<List<? extends UserDto>> findAll(@RequestParam(required = false) String rolesName) {

        List<UserDto> usersDto = userService.findAll(rolesName);

        return ResponseEntity.ok().body(usersDto);
    }

    @Override
    @GetMapping("/{id}")
    @Operation(summary = "Me retorna um User específico do sistema, não importa a role.")
    public ResponseEntity<UserDto> findById(@PathVariable Long id) {

        UserDto userDto = userService.findById(id);

        return ResponseEntity.ok().body(userDto);
    }

    @DeleteMapping(value = "/{id}/remove")
    @Operation(summary = "Remove um usuário do sistema.")
    public ResponseEntity<String> remove(@PathVariable Long id) {

        String message = userService.remove(id);

        return ResponseEntity.ok().body(message);
    }

}
