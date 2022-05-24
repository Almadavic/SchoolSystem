package application.dto;

import application.entity.ClassRoom;
import application.entity.ClassShift;
import application.entity.users.Student;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.ArrayList;
import java.util.List;

@JsonPropertyOrder({"idClass","letter","classShift","teacher","numberStudents","students","averageClassGrade"})
public class ClassRoomDto {

    @JsonProperty("idClass")
    private Long idClass;
    @JsonProperty("letter")
    private Character letter;

    @JsonProperty("classShift")
    private ClassShift classShift;
    @JsonProperty("teacher")
    private TeacherDto teacherDto;

    @JsonProperty("numberStudents")
    private Integer numberStudents;
    @JsonProperty("students")
    private List<StudentDto> studentsDto = new ArrayList<>();
    @JsonProperty("averageClassGrade")
    private Double averageClassGrade;

    public ClassRoomDto() {

    }


    public ClassRoomDto(ClassRoom classRoom) {
        this.idClass = classRoom.getId();
        this.letter = classRoom.getLetter();
        this.classShift = classRoom.getClassShift();
        if(classRoom.getTeacher()!=null) {
            this.teacherDto = new TeacherDto(classRoom.getTeacher());
        }
        this.numberStudents= classRoom.getStudents().size();
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
        return idClass;
    }

    public void setId(Long id) {
        this.idClass = id;
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
