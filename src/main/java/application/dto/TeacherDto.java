package application.dto;

import application.entity.users.Teacher;

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
