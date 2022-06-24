package application.dtos;

import application.entities.users.Teacher;
import application.entities.users.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonPropertyOrder(value = {"id", "name", "email", "classRoomId", "address", "roles", "registration"})
public class TeacherDto extends UserDto { // Dto de Teacher

    @JsonProperty(value = "classRoomId")
    private Long classRoomId;

    public TeacherDto(User user) {             // Método que transforma Teacher em TeacherDto
        super(user);
        Teacher teacher = (Teacher) user;
        if (teacher.getClassRoom() != null) {
            this.classRoomId = teacher.getClassRoom().getId();
        }
    }
}
