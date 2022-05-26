package application.dto;

import application.entity.users.Student;
import application.entity.users.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.validation.constraints.NotNull;

@JsonPropertyOrder({"id", "name", "email", "classRoomId", "reportCard", "address", "roles"})
public class StudentDto extends UserDto { // Dto da classe Student

    @JsonProperty(value = "reportCard")
    private ReportCardDto reportCardDto;

    @JsonProperty(value = "classRoomId")
    private Long classRoomId;

    public StudentDto() {

    }



    public StudentDto(User user) {  // Método para transformar um Student em StudentDto
        super(user);
        Student student = (Student) user;
        if (student.getClassRoom() != null) {
            this.classRoomId = student.getClassRoom().getId();
        }
        this.reportCardDto = new ReportCardDto(student.getReportCard());
    }


    public ReportCardDto getReportCardDto() {
        return reportCardDto;
    }

    public void setReportCardDto(ReportCardDto reportCardDto) {
        this.reportCardDto = reportCardDto;
    }

    public Long getClassRoomId() {
        return classRoomId;
    }

    public void setClassRoomId(Long classRoomId) {
        this.classRoomId = classRoomId;
    }
}
