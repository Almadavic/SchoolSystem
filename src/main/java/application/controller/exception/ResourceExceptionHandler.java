package application.controller.exception;

import application.service.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;

@ControllerAdvice
public class ResourceExceptionHandler {  // Se ocorrer alguma das execções abaixo durante o programa, o Spring vai cair nessa classe e vai retornar o erro
                                           // de uma forma mais agradavél pro cliente.
    @ExceptionHandler(ResourceNotFoundException.class)          // Caso algum recurso não seja encontrado.
    public ResponseEntity<StandardError> resourceNotFound(ResourceNotFoundException e, HttpServletRequest request) {
        String error = "Resource not found";
        HttpStatus status = HttpStatus.NOT_FOUND;
        StandardError err = new StandardError(Instant.now(),status.value(),error,e.getMessage(),request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(DatabaseException.class)  // Algum problema com o banco de dados.
    public ResponseEntity<StandardError> dataBase(DatabaseException e, HttpServletRequest request) {
        String error = "Database error";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError err = new StandardError(Instant.now(),status.value(),error,e.getMessage(),request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(NoPermissionException.class)        // Sem permissão para fazer alguma operação dentro do sistema.
    public ResponseEntity<StandardError> noPermission(NoPermissionException e, HttpServletRequest request) {
        String error = "No permission error";
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        StandardError err = new StandardError(Instant.now(),status.value(),error,e.getMessage(),request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(StudentAlreadyExists.class) // Quando tenta adicionar um usuário em uma classe em que ele já está adicionado.
    public ResponseEntity<StandardError> studentAlreadyExists(StudentAlreadyExists e, HttpServletRequest request) {
        String error = "Student already exists";
        HttpStatus status = HttpStatus.NOT_ACCEPTABLE;
        StandardError err = new StandardError(Instant.now(),status.value(),error,e.getMessage(),request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(GradeValueNotAllowed.class) // Quando tenta adicionar uma nota errada á um usuário.
    public ResponseEntity<StandardError> gradeValueNotAllowed(GradeValueNotAllowed e, HttpServletRequest request) {
        String error = "Value not allowed";
        HttpStatus status = HttpStatus.NOT_ACCEPTABLE;
        StandardError err = new StandardError(Instant.now(),status.value(),error,e.getMessage(),request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(NullPointerException.class) // Quando algo é nulo!
    public ResponseEntity<StandardError> nullPointerException(NullPointerException e, HttpServletRequest request) {
        String error = "Value cannot be null";
        HttpStatus status = HttpStatus.NOT_ACCEPTABLE;
        StandardError err = new StandardError(Instant.now(),status.value(),error,e.getMessage(),request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

}
