package application.controller;

import application.dto.UserDto;
import application.entity.User;
import application.service.UserService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<Page<UserDto>> findAll(
            @PageableDefault(sort = "id", direction = Sort.Direction.ASC, page = 0, size = 10) Pageable pagination)
    {
        Page<UserDto> usersDto = userService.findAll(pagination);
        return ResponseEntity.ok().body(usersDto);
    }

    @GetMapping ("/{id}")
    public ResponseEntity<UserDto> findById(@PathVariable Long id) {

        UserDto userDto = userService.findById(id);

        return ResponseEntity.ok().body(userDto);
    }

}
