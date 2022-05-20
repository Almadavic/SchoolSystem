package application.controller;

import application.dto.StudentDto;
import application.service.StudentService;
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

import java.security.Principal;

@RestController
@RequestMapping(value = "/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping(value = "/MyData")
    public ResponseEntity<StudentDto> myData(Principal principal) {

        StudentDto studentDto = studentService.myData(principal);

        return ResponseEntity.ok().body(studentDto);
    }

    @GetMapping
    public ResponseEntity<Page<StudentDto>> findAll(@PageableDefault(sort = "id", direction = Sort.Direction.ASC, page = 0, size = 10) Pageable pagination) {

        Page<StudentDto> studentDtos = studentService.findAll(pagination);

        return ResponseEntity.ok().body(studentDtos);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<StudentDto> findById(@PathVariable Long id) {

        StudentDto studentDto = studentService.findByID(id);

        return ResponseEntity.ok().body(studentDto);
    }

}
