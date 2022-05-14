package application.controller;

import application.dto.ClassRoomDto;
import application.entity.ClassRoom;
import application.service.ClassRoomService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
