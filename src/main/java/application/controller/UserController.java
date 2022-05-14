package application.controller;

import application.dto.UserDto;
import application.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<Page<UserDto>> findAll(
            @PageableDefault(sort = "id", direction = Sort.Direction.ASC, page = 0, size = 10) Pageable pagination,@RequestParam(required=false)String rolesName)
    {
        Page<UserDto> usersDto = userService.findAll(pagination,rolesName);
        return ResponseEntity.ok().body(usersDto);
    }

    @GetMapping ("/{id}")
    public ResponseEntity<UserDto> findById(@PathVariable Long id) {

        UserDto userDto = userService.findById(id);

        return ResponseEntity.ok().body(userDto);
    }

}
