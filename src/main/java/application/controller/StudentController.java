package application.controller;

import application.dto.StudentDto;
import application.service.serviceLayer.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/students")
public class StudentController {  // Controller para o diretor, apenas quem tem essa role, ver informações de todos os alunos do sistema para fazer controle!

    @Autowired
    private StudentService studentService;

    @GetMapping
    // Método q retorna todos os alunos do sistema registrados!
    public ResponseEntity<Page<StudentDto>> findAll(@PageableDefault(sort = "id", direction = Sort.Direction.ASC, page = 0, size = 10) Pageable pagination) {

        Page<StudentDto> studentDtos = studentService.findAll(pagination);

        return ResponseEntity.ok().body(studentDtos);
    }

    @GetMapping(value = "/{id}")
    //Método retorna um aluno em específico do sistema.
    public ResponseEntity<StudentDto> findById(@PathVariable Long id) {

        StudentDto studentDto = studentService.findById(id);

        return ResponseEntity.ok().body(studentDto);
    }

}
