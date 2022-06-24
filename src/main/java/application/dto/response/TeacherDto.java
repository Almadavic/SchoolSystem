package application.dto.response;

import application.domain.entity.user.Teacher;
import application.domain.entity.user.User;
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
