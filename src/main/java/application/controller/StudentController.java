package application.controller;

import application.dto.StudentDto;
import application.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping(value="/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping(value = "/MyData")
   public ResponseEntity<StudentDto> myData(Principal principal) {

        StudentDto studentDto = studentService.myData(principal);

        return ResponseEntity.ok().body(studentDto);
    }



}
