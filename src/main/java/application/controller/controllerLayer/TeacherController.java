package application.controller.controllerLayer;

import application.controller.controllerLayer.interfacee.ExtendsUserController;
import application.dto.StudentDto;
import application.dto.TeacherDto;
import application.dto.UserDto;
import application.form.RegisterUserForm;
import application.service.serviceLayer.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/teachers")
public class TeacherController implements ExtendsUserController { // Controller que apenas o diretor tem acesso, ele pode ver as informações de todos os professores do sistema para fazer controle.

    @Autowired
    private TeacherService teacherService;

    @Override
    @GetMapping
    // Método que retorna todos os professores do sistema.
    public ResponseEntity<Page<? extends UserDto>> findAll(@PageableDefault(sort = "id", direction = Sort.Direction.ASC, page = 0, size = 10) Pageable pagination,
                                                           @RequestParam(required = false) String noClass) {

        Page<TeacherDto> teacherDtos = teacherService.findAll(pagination, noClass);

        return ResponseEntity.ok().body(teacherDtos);
    }



    @Override
    @GetMapping("/{id}")
    // Método que retorna um teacher em específico,passando o  id.
    public ResponseEntity<TeacherDto> findById(@PathVariable Long id) {

        TeacherDto teacherDto = teacherService.findById(id);

        return ResponseEntity.ok().body(teacherDto);
    }

    @Override
    @PostMapping(value = "/register")
    // Cria um novo Teacher (cadastra) no banco de dados!
    public ResponseEntity<? extends UserDto> save(@RequestBody @Valid RegisterUserForm userForm, UriComponentsBuilder uriBuilder) {

        TeacherDto teacherDto = teacherService.save(userForm);

        URI uri = uriBuilder.path("/students/{id}").buildAndExpand(teacherDto.getId()).toUri();

        return ResponseEntity.created(uri).body(teacherDto);
    }


}
