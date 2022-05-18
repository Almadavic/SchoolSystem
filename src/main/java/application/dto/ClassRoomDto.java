package application.dto;

import application.entity.ClassRoom;
import application.entity.Student;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.ArrayList;
import java.util.List;

@JsonPropertyOrder({"id","letter","teacher","students","averageClassGrade"})
public class ClassRoomDto {

    @JsonProperty("id")
    private Long id;
    @JsonProperty("letter")
    private Character letter;
    @JsonProperty("teacher")
    private TeacherDto teacherDto;
    @JsonProperty("students")
    private List<StudentDto> studentsDto = new ArrayList<>();
    @JsonProperty("averageClassGrade")
    private Double averageClassGrade;

    public ClassRoomDto() {

    }


    public ClassRoomDto(ClassRoom classRoom) {
        this.id = classRoom.getId();
        this.letter = classRoom.getLetter();
        this.teacherDto = new TeacherDto(classRoom.getTeacher());
        for (Student student : classRoom.getStudents()) {
            studentsDto.add(new StudentDto(student));                // Método necessita de refatoração nessa parte
        }
        double sum = 0.0;
        for (StudentDto studentDto : studentsDto) {
            sum = sum + studentDto.getReportCardDto().getAverageGrade();
        }
       averageClassGrade = Math.round(sum / studentsDto.size()*10.0)/10.0;
    }

    public Long getIdClass() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Character getLetter() {
        return letter;
    }

    public void setLetter(Character letter) {
        this.letter = letter;
    }

    public TeacherDto getTeacher() {
        return teacherDto;
    }

    public void setTeacherDto(TeacherDto teacherDto) {
        this.teacherDto = teacherDto;
    }

    public List<StudentDto> getStudents() {
        return studentsDto;
    }


    public Double getAverageClassGrade() {
        return averageClassGrade;
    }

    public void setAverageClassGrade(Double averageClassGrade) {
        this.averageClassGrade = averageClassGrade;
    }
}
