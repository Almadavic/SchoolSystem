package application.dto;

import application.entity.users.Teacher;
import application.entity.users.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;

@Getter
@JsonPropertyOrder({"id","name","email","classRoomId","address","roles"})
public class TeacherDto extends UserDto { // Dto de Teacher

    @JsonProperty(value = "classRoomId")
    private Long classRoomId;

    public TeacherDto() {

    }

    public TeacherDto(User user) {             // Método que transforma Teacher em TeacherDto
        super(user);
        Teacher teacher = (Teacher) user;
        if(teacher.getClassRoom()!=null) {
            this.classRoomId = teacher.getClassRoom().getId();
        }
    }
}
