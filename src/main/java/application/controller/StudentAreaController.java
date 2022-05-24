package application.controller;

import application.dto.StudentDto;
import application.form.NewPasswordForm;
import application.service.serviceLayer.StudentAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping(value = "/StudentArea")
public class StudentAreaController { // Controller onde o usuário (APENAS ALUNO) tem acesso, seria como a area do aluno!

    @Autowired
    private StudentAreaService studentAreaService;

    @GetMapping(value = "/MyData")
    // Método para acessar as informações do proprio aluno logado.
    public ResponseEntity<StudentDto> myData(Principal principal) {

        StudentDto studentDto = studentAreaService.myData(principal);

        return ResponseEntity.ok().body(studentDto);
    }

    @PutMapping(value = "/ChangePassword")
    // Método para o aluno trocar o password, (AINDA NÃO FUNCIONAL!)
    public ResponseEntity<String> changePassword(Principal principal,@RequestBody @Valid NewPasswordForm newPasswordForm) {

        String newPassword = studentAreaService.changePassword(principal,newPasswordForm);

        return ResponseEntity.ok().body(newPassword);
    }
}

