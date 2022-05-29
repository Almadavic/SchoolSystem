package application.dto;

import application.entity.ClassRoom;
import application.entity.ClassShift;
import application.entity.users.Student;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@JsonPropertyOrder({"idClass", "letter", "classShift", "teacher", "numberStudents", "students", "averageClassGrade"})
public class ClassRoomDto {    // DTO da classe ClassRoom

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

    public ClassRoomDto(ClassRoom classRoom) {      // Transformando uma ClassRoom em ClassRoomDto
        this.idClass = classRoom.getId();
        this.letter = classRoom.getLetter();
        this.classShift = classRoom.getClassShift();
        this.studentsDto = convertList(classRoom.getStudents());
        this.numberStudents = studentsDto.size();
        this.averageClassGrade = classRoom.getAverageClass();
        if (classRoom.getTeacher() != null) {
            this.teacherDto = new TeacherDto(classRoom.getTeacher());
        }
    }

    private List<StudentDto> convertList(List<Student> students) {
        return students.stream().map(StudentDto::new).collect(Collectors.toList());
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


}
