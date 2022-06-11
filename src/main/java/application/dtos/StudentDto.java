package application.dtos;

import application.entities.users.Student;
import application.entities.users.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonPropertyOrder(value = {"id", "name", "email", "classRoomId", "reportCard", "address", "roles", "registration"})
public class StudentDto extends UserDto implements Comparable<StudentDto> { // Dto da classe Student

    @JsonProperty(value = "reportCard")
    private ReportCardDto reportCardDto;

    @JsonProperty(value = "classRoomId")
    private Long classRoomId;

    public StudentDto(User user) {  // Método para transformar um Student em StudentDto
        super(user);
        Student student = (Student) user;
        if (student.getClassRoom() != null) {
            this.classRoomId = student.getClassRoom().getId();
        }
        this.reportCardDto = new ReportCardDto(student.getReportCard());
    }

    @Override
    public int compareTo(StudentDto studentDto) {         // Método para comparar um estudante com outro baseado no nome, ou seja, ordem alfabética.
        return getName().compareTo(studentDto.getName());
    }
}
