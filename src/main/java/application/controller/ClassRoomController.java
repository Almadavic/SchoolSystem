package application.controller;

import application.dto.ClassRoomDto;
import application.dto.StudentDto;
import application.dto.TeacherDto;
import application.service.ClassRoomService;
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

import java.util.List;

@RestController
@RequestMapping("/classes")
public class ClassRoomController {

    @Autowired
    private ClassRoomService clasService;

    @GetMapping
    public ResponseEntity<Page<ClassRoomDto>> findAll(@PageableDefault(sort = "id", direction = Sort.Direction.ASC, page = 0, size = 10) Pageable pagination) {
        Page<ClassRoomDto> classes = clasService.findAll(pagination);
        return ResponseEntity.ok().body(classes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClassRoomDto> findById(@PathVariable Long id) {

        ClassRoomDto classRoomDto = clasService.findById(id);

        return ResponseEntity.ok().body(classRoomDto);
    }

    @GetMapping("/{id}/students")
    public ResponseEntity<List<StudentDto>> findStudentsByClass(@PathVariable Long id) {

        List<StudentDto> studentDtos = clasService.findStudentsByClass(id);

        return ResponseEntity.ok().body(studentDtos);
    }

    @GetMapping("/{idClass}/students/{idStudent}")
    public ResponseEntity<StudentDto> findStudentById(@PathVariable Long idClass, @PathVariable Long idStudent) {

        StudentDto studentDto = clasService.findStudentById(idClass,idStudent);

        return ResponseEntity.ok().body(studentDto);
    }

    @GetMapping("{id}/teacher")
    public ResponseEntity<TeacherDto> findTeacher(@PathVariable Long id) {
        TeacherDto teacherDto = clasService.findTeacher(id);
        return ResponseEntity.ok().body(teacherDto);
    }
}
