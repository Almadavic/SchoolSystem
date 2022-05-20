package application.controller;

import application.dto.ClassRoomDto;
import application.form.NewGradesForm;
import application.dto.StudentDto;
import application.dto.TeacherDto;
import application.service.ClassRoomService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.security.Principal;

@RestController                              // Identificando  que é um rest-controller
@RequestMapping(value = "/classes")       // Recurso para "encontrar" esse controller
public class ClassRoomController {

    @Autowired  // Injeção de dependencia automatica - > ClassRoomService
    private ClassRoomService classService;   //

    @GetMapping
    // Método HTTP (GET)  , Método que me retorna uma paginação de todas as classes da plataforma, tendo algumas conf padrões para essa paginação.
    public ResponseEntity<Page<ClassRoomDto>> findAll(@PageableDefault(sort = "id", direction = Sort.Direction.ASC, page = 0, size = 10) Pageable pagination) {

        Page<ClassRoomDto> classes = classService.findAll(pagination);

        return ResponseEntity.ok().body(classes);
    }

    @GetMapping(value = "/{id}")  // Método HTTP (GET) , Método me retorna uma classe da plataforma dado o Id da classe.
    public ResponseEntity<ClassRoomDto> findById(@PathVariable Long id) {

        ClassRoomDto classRoomDto = classService.findById(id);

        return ResponseEntity.ok().body(classRoomDto);
    }

    @GetMapping(value = "/{idClass}/students")
    // Método HTTP (GET) , Método me retorna uma lista de alunos associados á uma classe dado o id dessa classe.
    public ResponseEntity<Page<StudentDto>> findStudentsByClass(@PathVariable Long idClass,
                                                                @PageableDefault(sort = "id", direction = Sort.Direction.ASC, page = 0, size = 10) Pageable pagination){

        Page<StudentDto> studentsDtos = classService.findStudentsByClass(idClass,pagination);               // Arrumar o cache aqui !

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

    @PutMapping(value = "/{idClass}/students/{idStudent}/UpdateGrades")
    // // Método HTTP (PUT) , Método atualiza as notas de um aluno de uma respectiva sala, passando o id do aluno e da classe.
    public ResponseEntity<StudentDto> updateGrades(@PathVariable Long idClass, @PathVariable Long idStudent, Principal principal, // Me retorna o aluno com as notas atualizadas.
                                                   @RequestBody NewGradesForm newGrades) {

        StudentDto studentDto = classService.updateGrades(idClass, idStudent, principal, newGrades);

        return ResponseEntity.ok().body(studentDto);
    }

    @GetMapping(value = "/CreateClassRoom") // Método HTTP (GET) , Método cria uma nova sala na plataforma.
    public ResponseEntity<ClassRoomDto> createClassRoom(String classShift, UriComponentsBuilder uriBuilder) {

        ClassRoomDto classRoomDto = classService.createClassRoom(classShift);

        URI uri = uriBuilder.path("/classes/{id}").buildAndExpand(classRoomDto.getIdClass()).toUri();

        return ResponseEntity.created(uri).body(classRoomDto);
    }

    @PostMapping(value = "/{idClass}/setTeacher")      // Método HTTP (POST) , Método seta o professor em uma classe que não tenha professor.
    public ResponseEntity<ClassRoomDto> setTeacher(@PathVariable Long idClass, @RequestBody Long idTeacher) {

        ClassRoomDto classRoomDto = classService.setTeacher(idClass,idTeacher);               // Método ainda não funcional !!! Testar

        return ResponseEntity.ok().body(classRoomDto);
    }

    @PostMapping(value = "/{idClass}/addStudent")
    public ResponseEntity<ClassRoomDto> addStudent(@PathVariable Long idClass, @RequestBody Long idStudent) {  //Método HTTP (POST) , Método adiciona um aluno na classe.

        ClassRoomDto classRoomDto = classService.addStudent(idClass,idStudent);    // Método ainda não funciona !!!

        return ResponseEntity.ok().body(classRoomDto);
    }

}
