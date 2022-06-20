package application.controllers.controllerLayer;

import application.dtos.ClassRoomDto;
import application.forms.*;
import application.dtos.StudentDto;
import application.dtos.TeacherDto;
import application.services.serviceLayer.ClassRoomService;

import io.swagger.v3.oas.annotations.Operation;
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
@RequestMapping(value = "/classrooms")       // Recurso para "encontrar" esse controller
public class ClassRoomController {          // Controller relacionado á ações dentro de uma sistema de Sala de Aluno (Apenas relacionado ás salas de aula)

    @Autowired  // Injeção de dependencia automatica - > ClassRoomService
    private ClassRoomService classService;   //

    @GetMapping
    @Operation(summary = "Me retorna uma paginação de todas as classes do sistema.")
    public ResponseEntity<Page<ClassRoomDto>> findAll(@PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pagination) {

        Page<ClassRoomDto> classesDto = classService.findAll(pagination);

        return ResponseEntity.ok().body(classesDto);
    }

    @GetMapping(value = "/{id}")
    @Operation(summary = "Me retorna uma classe em específico.")
    public ResponseEntity<ClassRoomDto> findById(@PathVariable Long id, Principal user) {

        ClassRoomDto classRoomDto = classService.findById(id, user);

        return ResponseEntity.ok().body(classRoomDto);
    }

    @GetMapping(value = "/{idClass}/students")
    @Operation(summary = "Me retorna uma lista de alunos associados a uma classe.")
    public ResponseEntity<Page<StudentDto>> findStudentsByClass(@PathVariable Long idClass, @PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pagination, Principal user) {

        Page<StudentDto> studentsDto = classService.findStudentsByClass(idClass, pagination, user);

        return ResponseEntity.ok().body(studentsDto);
    }

    @GetMapping(value = "/{idClass}/students/{idStudent}")
    @Operation(summary = "Me retorna um aluno específico de uma classe específica.")
    public ResponseEntity<StudentDto> findStudentById(@PathVariable Long idClass, @PathVariable Long idStudent, Principal user) {

        StudentDto studentDto = classService.findStudentById(idClass, idStudent, user);

        return ResponseEntity.ok().body(studentDto);
    }

    @GetMapping(value = "{idClass}/teacher")
    @Operation(summary = "Me retorna o professor de uma classe.")
    public ResponseEntity<TeacherDto> findTeacher(@PathVariable Long idClass, Principal user) {

        TeacherDto teacherDto = classService.findTeacher(idClass, user);

        return ResponseEntity.ok().body(teacherDto);
    }

    @PutMapping(value = "/{idClass}/students/{idStudent}/updategrades")
    @Operation(summary = "Atualiza as notas de um aluno de uma sala.")
    public ResponseEntity<StudentDto> updateGrades(@PathVariable Long idClass, @PathVariable Long idStudent, Principal user, @RequestBody NewGradesForm newGrades) {

        StudentDto studentDto = classService.updateGrades(idClass, idStudent, user, newGrades);

        return ResponseEntity.ok().body(studentDto);
    }

    @PostMapping(value = "/createclassroom")
    @Operation(summary = "Cria uma nova sala de aula no sistema.")
    public ResponseEntity<ClassRoomDto> createClassRoom(@RequestBody @Valid CreateClassForm createClassForm, UriComponentsBuilder uriBuilder) {

        ClassRoomDto classRoomDto = classService.createClassRoom(createClassForm);

        URI uri = uriBuilder.path("/classes/{id}").buildAndExpand(classRoomDto.getIdClass()).toUri();

        return ResponseEntity.created(uri).body(classRoomDto);
    }

    @PutMapping(value = "/{idClass}/setteacher")
    @Operation(summary = "Seta (coloca) o professor em uma classe.")
    public ResponseEntity<ClassRoomDto> setTeacher(@PathVariable Long idClass, @RequestBody @Valid SetTeacherForm setTeacherForm) {

        ClassRoomDto classRoomDto = classService.setTeacher(idClass, setTeacherForm);

        return ResponseEntity.ok().body(classRoomDto);
    }

    @PutMapping(value = "/{idClass}/addstudent")
    @Operation(summary = "Adiciona um aluno em uma classe.")
    public ResponseEntity<ClassRoomDto> addStudent(@PathVariable Long idClass, @RequestBody @Valid AddRemoveStudentForm addStudentForm) {

        ClassRoomDto classRoomDto = classService.addStudent(idClass, addStudentForm);

        return ResponseEntity.ok().body(classRoomDto);
    }

    @PutMapping(value = "/{idClass}/removestudent")
    @Operation(summary = "Remove um aluno de uma classe.")
    public ResponseEntity<ClassRoomDto> removeStudent(@PathVariable Long idClass, @RequestBody @Valid AddRemoveStudentForm removeStudentForm) {

        ClassRoomDto classRoomDto = classService.removeStudent(idClass, removeStudentForm);

        return ResponseEntity.ok().body(classRoomDto);
    }

    @PutMapping(value = "/{idClass}/removeteacher")
    @Operation(summary = "Remove o professor de uma sala.")
    public ResponseEntity<ClassRoomDto> removeTeacher(@PathVariable Long idClass) {

        ClassRoomDto classRoomDto = classService.removeTeacher(idClass);

        return ResponseEntity.ok().body(classRoomDto);
    }

    @DeleteMapping(value = "/{idClass}/removeclass")
    @Operation(summary = "Apaga uma classe do sistema.")
    public ResponseEntity<String> removeClass(@PathVariable Long idClass) {

        String message = classService.removeClass(idClass);

        return ResponseEntity.ok().body(message);
    }


}
