package application.controller.controllerLayer;

import application.controller.controllerLayer.interfacee.ExtendsUserController;
import application.dto.StudentDto;
import application.dto.UserDto;
import application.form.RegisterUserForm;
import application.service.serviceLayer.StudentService;
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
@RequestMapping(value = "/students")
public class StudentController implements ExtendsUserController {  // Controller para o diretor, apenas quem tem essa role, ver informações de todos os alunos do sistema para fazer controle!

    @Autowired
    private StudentService studentService;

    @Override
    @GetMapping
    // Método q retorna todos os alunos do sistema registrados!
    public ResponseEntity<Page<? extends UserDto>> findAll(@PageableDefault(sort = "id", direction = Sort.Direction.ASC, page = 0, size = 10) Pageable pagination,
                                                           @RequestParam(required = false) String noClass) {

        Page<StudentDto> studentDtos = studentService.findAll(pagination, noClass);

        return ResponseEntity.ok().body(studentDtos);
    }

    @Override
    @GetMapping(value = "/{id}")
   // Método retorna um aluno em específico do sistema.
   public ResponseEntity<StudentDto> findById(@PathVariable Long id) {

        StudentDto studentDto = studentService.findById(id);

       return ResponseEntity.ok().body(studentDto);
    }

    @Override
    @PostMapping(value = "/register")
    // Cria um novo Estudante(cadastra) no banco de dados!
    public ResponseEntity<? extends UserDto> save(@RequestBody @Valid  RegisterUserForm userForm, UriComponentsBuilder uriBuilder) {

        StudentDto studentDto = studentService.save(userForm);

        URI uri = uriBuilder.path("/students/{id}").buildAndExpand(studentDto.getId()).toUri();


        return ResponseEntity.created(uri).body(studentDto);
    }


}