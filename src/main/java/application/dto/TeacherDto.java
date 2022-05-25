package application.dto;

import application.entity.users.Teacher;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"id","name","email","classRoomId","address","roles"})
public class TeacherDto extends UserDto { // Dto de Teacher

    @JsonProperty(value = "classRoomId")
    private Long classRoomId;

    public TeacherDto() {

    }

    public TeacherDto(Teacher teacher) { // Método que transforma Teacher em TeacherDto
        this.setId(teacher.getId());
        this.setName(teacher.getName());
        this.setEmail(teacher.getEmail());
        if(teacher.getClassRoom()!=null) {

            this.classRoomId = teacher.getClassRoom().getId();
       }
        this.setAddressDto(new AddressDto(teacher.getAddress()));
        convertList(teacher.getAuthorities());
    }
}
