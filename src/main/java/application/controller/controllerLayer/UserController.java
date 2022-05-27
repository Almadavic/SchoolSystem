package application.controller.controllerLayer;

import application.controller.controllerLayer.interfacee.AllUserTypeController;
import application.dto.UserDto;
import application.service.serviceLayer.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/users")
public class UserController implements AllUserTypeController { // Controller para verificar todos os usuários do sistema. Apenas o diretor tem acesso aqui! Para fazer controle.

    @Autowired
    private UserService userService;

    @Override
    @GetMapping
    //Método retorna todos os usuários do sistema, ou eu posso filtrar a busca (PELA ROLE),ex : todos os usuarios que tenham a role professor..
    public ResponseEntity<Page<? extends UserDto>> findAll(
            @PageableDefault(sort = "id", direction = Sort.Direction.ASC, page = 0, size = 10) Pageable pagination,@RequestParam(required=false)String rolesName){

        Page<UserDto> usersDto = userService.findAll(pagination,rolesName);

        return ResponseEntity.ok().body(usersDto);
    }

    @Override
    @GetMapping ("/{id}")
    // Método que me retorna um User especifico do sistema, não importa a role!
    public ResponseEntity<UserDto> findById(@PathVariable Long id) {

        UserDto userDto = userService.findById(id);

        return ResponseEntity.ok().body(userDto);
    }
    @DeleteMapping(value = "/{id}/remove")
    public ResponseEntity<String> remove(@PathVariable Long id) {

        String message = userService.remove(id);

        return ResponseEntity.ok().body(message);
    }

}