package application.controllers.controllerLayer;

import application.controllers.controllerLayer.interfaces.ExtendsUserController;
import application.dtos.TeacherDto;
import application.dtos.UserDto;
import application.forms.RegisterUserForm;
import application.services.serviceLayer.TeacherService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/teachers")
public class TeacherController implements ExtendsUserController { // Controller que apenas o diretor tem acesso, ele pode ver as informações de todos os professores do sistema para fazer controle.

    @Autowired
    private TeacherService teacherService;

    @Override
    @GetMapping
    @Operation(summary = "Me retorna todos os professores do sistema, ou eu posso filtrar a busca (PROFESSORES QUE NÃO TEM CLASSE ASSOCIADA).")
    public ResponseEntity<List<? extends UserDto>> findAll(@RequestParam(required = false) String noClass) {

        List<TeacherDto> teachersDto = teacherService.findAll(noClass);

        return ResponseEntity.ok().body(teachersDto);
    }


    @Override
    @GetMapping("/{id}")
    @Operation(summary = "Me retorna um teacher em específico,passando o  id.")
    public ResponseEntity<TeacherDto> findById(@PathVariable Long id) {

        TeacherDto teacherDto = teacherService.findById(id);

        return ResponseEntity.ok().body(teacherDto);
    }

    @Override
    @PostMapping(value = "/register")
    @Operation(summary = "Cria um novo Teacher (cadastra) no banco de dados.")
    public ResponseEntity<? extends UserDto> save(@RequestBody @Valid RegisterUserForm userForm, UriComponentsBuilder uriBuilder) {

        TeacherDto teacherDto = teacherService.save(userForm);

        URI uri = uriBuilder.path("/students/{id}").buildAndExpand(teacherDto.getId()).toUri();

        return ResponseEntity.created(uri).body(teacherDto);
    }


}
