package application.dto;

import application.entity.Teacher;

import java.util.ArrayList;
import java.util.List;

public class TeacherDto extends UserDto {

    public TeacherDto() {

    }

    public TeacherDto(Teacher teacher) {
        this.setId(teacher.getId());
        this.setName(teacher.getName());
        this.setEmail(teacher.getEmail());
        this.setAddressDto(new AddressDto(teacher.getAddress()));
        convertList(teacher.getAuthorities());
    }
}
