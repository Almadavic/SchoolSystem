package application.controllers.controllerLayer;

import application.controllers.controllerLayer.interfaces.ExtendsUserController;
import application.dtos.StudentDto;
import application.dtos.UserDto;
import application.forms.RegisterUserForm;
import application.services.serviceLayer.StudentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/students")
public class StudentController implements ExtendsUserController {  // Controller para o diretor, apenas quem tem essa role,consegue
    // ver informações de todos os alunos do sistema para fazer algum tipo de controle!

    @Autowired
    private StudentService studentService;

    @Override
    @GetMapping
    // Método que retorna todos os alunos do sistema registrados, ou eu posso filtrar a busca (ALUNOS QUE NÃO TEM CLASSE ASSOCIADA).
    public ResponseEntity<List<? extends UserDto>> findAll(@RequestParam(required = false) String noClass) {

        List<StudentDto> studentsDto = studentService.findAll(noClass);

        return ResponseEntity.ok().body(studentsDto);
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
    // Método cria (cadastra) um novo estudante no banco de dados.
    public ResponseEntity<? extends UserDto> save(@RequestBody @Valid RegisterUserForm userForm, UriComponentsBuilder uriBuilder) {

        StudentDto studentDto = studentService.save(userForm);

        URI uri = uriBuilder.path("/students/{id}").buildAndExpand(studentDto.getId()).toUri();

        return ResponseEntity.created(uri).body(studentDto);
    }


}
