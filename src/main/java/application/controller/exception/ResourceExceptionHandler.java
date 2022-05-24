package application.controller.exception;

import application.service.exception.classRoom.reportCard.GradeValueNotAllowed;
import application.service.exception.classRoom.reportCard.NoPermissionException;
import application.service.exception.classRoom.students.StudentBelongsSameClass;
import application.service.exception.classRoom.students.StudentBelongsAnotherClass;
import application.service.exception.classRoom.teachers.TeacherBelongsAnotherClass;
import application.service.exception.database.DatabaseException;
import application.service.exception.database.ResourceNotFoundException;
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
        StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(DatabaseException.class)  // Algum problema com o banco de dados.
    public ResponseEntity<StandardError> dataBase(DatabaseException e, HttpServletRequest request) {
        String error = "Database error";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(NoPermissionException.class)        // Sem permissão para fazer alguma operação dentro do sistema.
    public ResponseEntity<StandardError> noPermission(NoPermissionException e, HttpServletRequest request) {
        String error = "No permission error";
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(StudentBelongsSameClass.class)
    // Quando tenta adicionar um usuário em uma classe em que ele já está adicionado.
    public ResponseEntity<StandardError> studentAlreadyExists(StudentBelongsSameClass e, HttpServletRequest request) {
        String error = "Student already exists";
        HttpStatus status = HttpStatus.NOT_ACCEPTABLE;
        StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(TeacherBelongsAnotherClass.class)
    // Quando tentam setar o professor na mesma classe que ele pertence.
    public ResponseEntity<StandardError> teacherBelongsAnotherClass(TeacherBelongsAnotherClass e, HttpServletRequest request) {
        String error = "Student already exists";
        HttpStatus status = HttpStatus.NOT_ACCEPTABLE;
        StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(StudentBelongsAnotherClass.class)
    // Quando tenta adicionar um usuário em uma classe em que ele já está adicionado.
    public ResponseEntity<StandardError> studentBelongsAnotherClass(StudentBelongsAnotherClass e, HttpServletRequest request) {
        String error = "Student already has a class.";
        HttpStatus status = HttpStatus.NOT_ACCEPTABLE;
        StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(GradeValueNotAllowed.class) // Quando tenta adicionar uma nota incorreta á um usuário.
    public ResponseEntity<StandardError> gradeValueNotAllowed(GradeValueNotAllowed e, HttpServletRequest request) {
        String error = "Value not allowed";
        HttpStatus status = HttpStatus.NOT_ACCEPTABLE;
        StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(NullPointerException.class) // Quando algo é nulo que não poderia ser!
    public ResponseEntity<StandardError> nullPointerException(NullPointerException e, HttpServletRequest request) {
        String error = "Value cannot be null";
        HttpStatus status = HttpStatus.NOT_ACCEPTABLE;
        StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(IllegalArgumentException.class) // Quando passa alguma informação errada!
    public ResponseEntity<StandardError> illegalArgumentException(IllegalArgumentException e, HttpServletRequest request) {
        String error = "Value wrong, pass an another type!";
        HttpStatus status = HttpStatus.NOT_ACCEPTABLE;
        StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

}
