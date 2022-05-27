package application.controller.controllerLayer;

import application.controller.controllerLayer.interfacee.GenericMethodController;
import application.dto.ClassRoomDto;
import application.form.*;
import application.dto.StudentDto;
import application.dto.TeacherDto;
import application.service.serviceLayer.ClassRoomService;

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
import java.security.Principal;

@RestController                              // Identificando  que é um rest-controller
@RequestMapping(value = "/classes")       // Recurso para "encontrar" esse controller
public class ClassRoomController implements GenericMethodController {          // Controller relacionado á ações dentro de uma sistema de Sala de Aluno (Apenas relacionado ás salas de aula)

    @Autowired  // Injeção de dependencia automatica - > ClassRoomService
    private ClassRoomService classService;   //

    @GetMapping
    // Método HTTP (GET)  , Método que me retorna uma paginação de todas as classes da plataforma, tendo algumas conf padrões para essa paginação.
    public ResponseEntity<Page<ClassRoomDto>> findAll(@PageableDefault(sort = "id", direction = Sort.Direction.ASC, page = 0, size = 10) Pageable pagination) {

        Page<ClassRoomDto> classes = classService.findAll(pagination);

        return ResponseEntity.ok().body(classes);
    }

    @Override
    @GetMapping(value = "/{id}")
    // Método HTTP (GET) , Método me retorna uma classe da plataforma dado o Id da classe.
    public ResponseEntity<ClassRoomDto> findById(@PathVariable Long id) {

        ClassRoomDto classRoomDto = classService.findById(id);

        return ResponseEntity.ok().body(classRoomDto);
    }

    @GetMapping(value = "/{idClass}/students")
    // Método HTTP (GET) , Método me retorna uma lista de alunos associados á uma classe dado o id dessa classe.
    public ResponseEntity<Page<StudentDto>> findStudentsByClass(@PathVariable Long idClass, @PageableDefault(sort = "id", direction = Sort.Direction.ASC, page = 0, size = 10) Pageable pagination) {

        Page<StudentDto> studentsDtos = classService.findStudentsByClass(idClass, pagination);               // Arrumar o cache aqui ! Cache não está funcionando!

        return ResponseEntity.ok().body(studentsDtos);
    }

    @GetMapping(value = "/{idClass}/students/{idStudent}")
    // Método HTTP (GET) , Método me retorna um aluno de uma certa classe, passando o id da classe e do aluno específico.
    public ResponseEntity<StudentDto> findStudentById(@PathVariable Long idClass, @PathVariable Long idStudent) {

        StudentDto studentDto = classService.findStudentById(idClass, idStudent);

        return ResponseEntity.ok().body(studentDto);
    }

    @GetMapping(value = "{idClass}/teacher")
    // Método HTTP (GET) , Método me retorna o professor associado á classe dado o id da classe.
    public ResponseEntity<TeacherDto> findTeacher(@PathVariable Long idClass) {

        TeacherDto teacherDto = classService.findTeacher(idClass);

        return ResponseEntity.ok().body(teacherDto);
    }

    @PutMapping(value = "/{idClass}/students/{idStudent}/updategrades")
    // // Método HTTP (PUT) , Método atualiza as notas de um aluno de uma respectiva sala, passando o id do aluno e da classe.
    // Me retorna o aluno com as notas atualizadas.
    public ResponseEntity<StudentDto> updateGrades(@PathVariable Long idClass, @PathVariable Long idStudent, Principal user, @RequestBody NewGradesForm newGrades) {

        StudentDto studentDto = classService.updateGrades(idClass, idStudent, user, newGrades);

        return ResponseEntity.ok().body(studentDto);
    }

    @PostMapping (value = "/createclassroom")
    // Método HTTP (GET) , Método cria uma nova sala na plataforma, com a info Letter automatizada.
    public ResponseEntity<ClassRoomDto> createClassRoom(@RequestBody @Valid CreateClassForm createClassForm, UriComponentsBuilder uriBuilder) {

        ClassRoomDto classRoomDto = classService.createClassRoom(createClassForm);

        URI uri = uriBuilder.path("/classes/{id}").buildAndExpand(classRoomDto.getIdClass()).toUri();

        return ResponseEntity.created(uri).body(classRoomDto);
    }

    @PutMapping(value = "/{idClass}/setteacher")
    // Método HTTP (PUT) , Método seta o professor em uma classe.
    public ResponseEntity<ClassRoomDto> setTeacher(@PathVariable Long idClass, @RequestBody @Valid SetTeacherForm setTeacherForm) {

        ClassRoomDto classRoomDto = classService.setTeacher(idClass, setTeacherForm);

        return ResponseEntity.ok().body(classRoomDto);
    }

    @PutMapping(value = "/{idClass}/addstudent")
    //Método HTTP (PUT) , Método adiciona um aluno na classe.
    public ResponseEntity<ClassRoomDto> addStudent(@PathVariable Long idClass, @RequestBody @Valid AddRemoveStudentForm addStudentForm) {

        ClassRoomDto classRoomDto = classService.addStudent(idClass, addStudentForm);

        return ResponseEntity.ok().body(classRoomDto);
    }

    @PutMapping(value = "/{idClass}/removestudent")
    //Método HTTP (PUT) , Método remove um aluno da classe.
    public ResponseEntity<ClassRoomDto> removeStudent(@PathVariable Long idClass, @RequestBody @Valid AddRemoveStudentForm removeStudentForm) {

        ClassRoomDto classRoomDto = classService.removeStudent(idClass, removeStudentForm);

        return ResponseEntity.ok().body(classRoomDto);
    }

    @PutMapping(value = "/{idClass}/removeteacher")
    // Método HTTP (PUT) , Método remove o professor da sala.
    public ResponseEntity<ClassRoomDto> removeTeacher(@PathVariable Long idClass) {

        ClassRoomDto classRoomDto = classService.removeTeacher(idClass);

        return ResponseEntity.ok().body(classRoomDto);
    }

    @DeleteMapping(value = "/{idClass}/removeclass")
    // Método HTTP ( DELETE) Apaga uma classe do sistema!
    public ResponseEntity<String> removeClass(@PathVariable Long idClass) {

        String message = classService.removeClass(idClass);

        return ResponseEntity.ok().body(message);
    }

}
