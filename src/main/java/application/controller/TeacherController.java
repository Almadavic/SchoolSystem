package application.controller;

import application.dto.TeacherDto;
import application.service.serviceLayer.TeacherService;
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
@RequestMapping("/teachers")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @GetMapping
    private ResponseEntity<Page<TeacherDto>> findAll(@PageableDefault(sort = "id", direction = Sort.Direction.ASC, page = 0, size = 10) Pageable pagination){

            Page<TeacherDto> teacherDtos = teacherService.findAll(pagination);

            return ResponseEntity.ok().body(teacherDtos);
    }

    @GetMapping("/{id}")
    private ResponseEntity<TeacherDto> findById(@PathVariable Long id) {

        TeacherDto teacherDto = teacherService.findByID(id);

        return ResponseEntity.ok().body(teacherDto);
    }
}
